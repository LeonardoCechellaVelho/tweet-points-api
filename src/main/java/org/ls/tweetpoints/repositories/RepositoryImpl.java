package org.ls.tweetpoints.repositories;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ls.tweetpoints.config.AppException;
import org.ls.tweetpoints.config.SurrealConfig;
import org.ls.tweetpoints.data.entities.Campaign;
import org.ls.tweetpoints.data.entities.CurrentCampaign;
import org.ls.tweetpoints.data.entities.Tweet;
import org.ls.tweetpoints.data.entities.User;
import org.ls.tweetpoints.data.enums.Errors;
import org.ls.tweetpoints.data.enums.Operations;
import org.ls.tweetpoints.data.models.CampaignModel;
import org.ls.tweetpoints.data.models.TweetModel;
import org.ls.tweetpoints.data.models.UserModel;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class RepositoryImpl implements Repository {

    private SurrealConfig driver;

    @Override
    public Campaign persistCampaign(Campaign campaign) {
        if (this.findCampaign(campaign.getPhrase()).isEmpty()) {
            return driver.database().create("campaign", campaign);
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"Phrase already registered");
        }
    }

    public Campaign setCurrentCampaign(Campaign campaign) {
        if (!this.findCampaign(campaign.getPhrase()).isEmpty()) {
            return driver.database().create("currentCampaign", new CurrentCampaign(campaign, LocalDateTime.now())).getCampaign();
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"Campaign does not exists");
        }
    }
    
    @Override
    public Campaign getCurrentCampaign() {
        List<CurrentCampaign> currentCampaign = this.findCurrentCampaign();
        if (!currentCampaign.isEmpty()) {
            return currentCampaign.get(0).getCampaign();
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"There are no active campaigns");
        }
    }
    
    @Override
    public Campaign updateCampaign(CampaignModel request) {
        List<Campaign> campaignNewFound = this.findCampaign(request.getNewPhrase());
        if (campaignNewFound.isEmpty()) {
            List<Campaign> campaignCurrentFound = this.findCampaign(request.getCurrentPhrase());
            if (!campaignCurrentFound.isEmpty()) {
                return this.updateCampaignCurrentPhraseFound(request, campaignCurrentFound.get(0));
            } else {
                throw new AppException(Errors.BAD_REQUEST.getCode(),"Campaign does not exist");
            }
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"New campaign phrase already exists");
        }
    }

    @Override
    public Tweet persistTweet(TweetModel tweetModel) {
        List<User> userFound = this.findUser(tweetModel.getUser().getEmail());
        List<CurrentCampaign> currentCampaign = this.findCurrentCampaign();
        if (!currentCampaign.isEmpty()) {
            if (tweetModel.getPayload().equals(currentCampaign.get(0).getCampaign().getPhrase())) {
                if (!userFound.isEmpty()) {
                    return createTweet(tweetModel, userFound);
                } else {
                    throw new AppException(Errors.BAD_REQUEST.getCode(),"User not found");
                }
            } else {
                throw new AppException(Errors.BAD_REQUEST.getCode(),"The tweeted campaign is not the current one");
            }
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"There are no active campaigns");
        }
    }

    @Override
    public User persistUser(User user) {
        if (this.findUser(user.getEmail()).isEmpty()) {
            return driver.database().create("user", user);
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"E-mail already registered");
        }
    }
    
    @Override
    public User getUser(UserModel userModel) {
        List<User> user = this.findUser(userModel.getEmail());
        if (!user.isEmpty()) {
            return user.get(0);
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"User not found");
        }
    }
    
    @Override
    public List<Tweet> getUserTweets(UserModel userModel) {
        List<Tweet> tweets = this.findTweetsByEmail(userModel.getEmail());
        if (!tweets.isEmpty()) {
            return tweets;
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"User does not have tweets");
        }
    }

    private Campaign updateCampaignCurrentPhraseFound(CampaignModel request, Campaign campaignFound) {
        Campaign campaign = campaignFound;
        campaign.setPhrase(request.getNewPhrase());
        List<CurrentCampaign> currentCampaignList = this.findCurrentCampaigns(request.getCurrentPhrase());
        if (!currentCampaignList.isEmpty()) {
            List<Tweet> tweetsFound = this.findTweetsByPayload(request.getCurrentPhrase());
            if (!tweetsFound.isEmpty()) {
                for (Tweet tweet : tweetsFound) {
                    if (this.wasCurrentCampaign(tweet.getTimestamp().toString(), tweet.getPayload())) {
                        List<User> userFound = this.findUser(tweet.getUser().getEmail());
                        if (!userFound.isEmpty()) {
                            User user = userFound.get(0);
                            this.updateUserPoints(tweet, user, Operations.SUBTRACT);
                        }
                    }
                }
            }
            for (CurrentCampaign currentCampaign : currentCampaignList) {
                currentCampaign.setCampaign(campaign);
                driver.database().update("currentCampaign", currentCampaign);
            }
        }
        return driver.database().update("campaign", campaign).get(0);
    }

    private Tweet createTweet(TweetModel tweetModel, List<User> userFound) {
        User user = userFound.get(0);
        Tweet tweet = Tweet.from(tweetModel, user);
        this.updateUserPoints(tweet, user, Operations.INCREMENT);
        return driver.database().create("tweet", tweet);
    }

    private void updateUserPoints(Tweet tweet, User user, Operations operation) {
        if (this.findTweet(tweet).isEmpty()) {
            user.setPoints(Operations.INCREMENT.equals(operation) ? user.getPoints() + 10 : user.getPoints() - 10);
            driver.database().update("user", user);
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"E-mail already has participated in this campaign");
        }
    }

    private List<User> findUser(String email) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        return driver.database().query("SELECT * FROM user WHERE email = $email", params, User.class).get(0).getResult();
    }

    private List<Tweet> findTweet(Tweet tweet) {
        Map<String, String> params = new HashMap<>();
        params.put("email", tweet.getUser().getEmail());
        params.put("payload", tweet.getPayload());
        return driver.database().query("SELECT * FROM tweet WHERE user.email = $email and payload = $payload", params, Tweet.class).get(0).getResult();
    }

    private List<Tweet> findTweetsByPayload(String payload) {
        Map<String, String> params = new HashMap<>();
        params.put("payload", payload);
        return driver.database().query("SELECT * FROM tweet WHERE payload = $payload", params, Tweet.class).get(0).getResult();
    }

    private List<Tweet> findTweetsByEmail(String email) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        return driver.database().query("SELECT * FROM tweet WHERE user.email = $email", params, Tweet.class).get(0).getResult();
    }

    private List<Campaign> findCampaign(String phrase) {
        Map<String, String> params = new HashMap<>();
        params.put("phrase", phrase);
        return driver.database().query("SELECT * FROM campaign WHERE phrase = $phrase", params, Campaign.class).get(0).getResult();
    }

    private List<CurrentCampaign> findCurrentCampaign() {
        return driver.database().query("SELECT * FROM currentCampaign ORDER BY timestamp DESC LIMIT 1", null, CurrentCampaign.class).get(0).getResult();
    }

    private List<CurrentCampaign> findCurrentCampaigns(String phrase) {
        Map<String, String> params = new HashMap<>();
        params.put("phrase", phrase);
        return driver.database().query("SELECT * FROM currentCampaign WHERE campaign.phrase = $phrase ORDER BY timestamp DESC", params, CurrentCampaign.class).get(0).getResult();
    }

    private boolean wasCurrentCampaign(String timestamp, String phrase) {
        String previousQuery = "SELECT * FROM currentCampaign WHERE timestamp <= :timestamp ORDER BY timestamp DESC LIMIT 1";

        Map<String, String> params = new HashMap<>();
        params.put("timestamp", timestamp);
        List<CurrentCampaign> previousCampaign = driver.database().query(previousQuery, params, CurrentCampaign.class).get(0).getResult();
        
        if (!previousCampaign.isEmpty()) {
            CurrentCampaign campaignWasCurrent = previousCampaign.get(0);
            return phrase.equals(campaignWasCurrent.getCampaign().getPhrase());
        }
        return false;
    }
}
