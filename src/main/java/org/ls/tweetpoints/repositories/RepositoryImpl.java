package org.ls.tweetpoints.repositories;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.ls.tweetpoints.data.enums.HttpErrors;
import org.ls.tweetpoints.data.enums.Operations;
import org.ls.tweetpoints.data.models.NewCampaignModel;
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
            throw new AppException(HttpErrors.BAD_REQUEST.getCode(), Errors.PHRASE_ALREADY_REGISTERED);
        }
    }

    @Override
    public Campaign setCurrentCampaign(Campaign campaign) {
        List<Campaign> campaignFound = this.findCampaign(campaign.getPhrase());
        if (!campaignFound.isEmpty()) {
            this.verifyCampaignPhrase(campaign);
            return driver.database().create("currentCampaign", new CurrentCampaign(null, campaign, LocalDateTime.now())).getCampaign();
        } else {
            throw new AppException(HttpErrors.BAD_REQUEST.getCode(),Errors.CAMPAIGN_DOES_NOT_EXIST);
        }
    }
    
    @Override
    public Campaign getCurrentCampaign() {
        List<CurrentCampaign> currentCampaign = this.findCurrentCampaign();
        if (!currentCampaign.isEmpty()) {
            return currentCampaign.get(0).getCampaign();
        } else {
            throw new AppException(HttpErrors.BAD_REQUEST.getCode(), Errors.NO_ACTIVE_CAMPAIGNS);
        }
    }
    
    @Override
    public Campaign updateCampaign(NewCampaignModel request) {
        List<Campaign> campaignNewFound = this.findCampaign(request.getNewPhrase());
        if (campaignNewFound.isEmpty()) {
            List<Campaign> campaignCurrentFound = this.findCampaign(request.getCurrentPhrase());
            if (!campaignCurrentFound.isEmpty()) {
                return this.updateCampaignCurrentPhraseFound(request, campaignCurrentFound.get(0));
            } else {
                throw new AppException(HttpErrors.BAD_REQUEST.getCode(), Errors.CAMPAIGN_DOES_NOT_EXIST);
            }
        } else {
            throw new AppException(HttpErrors.BAD_REQUEST.getCode(), Errors.NEW_CAMPAIGN_PHRASE_EXISTS);
        }
    }

    @Override
    public Tweet persistTweet(TweetModel tweetModel) {
        List<User> userFound = this.findUser(tweetModel.getUser().getEmail());
        List<CurrentCampaign> currentCampaign = this.findCurrentCampaign();
        this.verifyTweet(tweetModel, userFound, currentCampaign);
        return createTweet(tweetModel, userFound);
    }

    @Override
    public User persistUser(User user) {
        if (this.findUser(user.getEmail()).isEmpty()) {
            return driver.database().create("user", user);
        } else {
            throw new AppException(HttpErrors.BAD_REQUEST.getCode(), Errors.EMAIL_ALREADY_REGISTERED);
        }
    }
    
    @Override
    public User getUser(UserModel userModel) {
        List<User> user = this.findUser(userModel.getEmail());
        if (!user.isEmpty()) {
            return user.get(0);
        } else {
            throw new AppException(HttpErrors.BAD_REQUEST.getCode(), Errors.USER_NOT_FOUND);
        }
    }
    
    @Override
    public List<Tweet> getUserTweets(UserModel userModel) {
        List<Tweet> tweets = this.findTweetsByEmail(userModel.getEmail());
        if (!tweets.isEmpty()) {
            return tweets;
        } else {
            throw new AppException(HttpErrors.BAD_REQUEST.getCode(), Errors.USER_DOES_NOT_HAVE_TWEETS);
        }
    }

    private void verifyTweet(TweetModel tweetModel, List<User> userFound, List<CurrentCampaign> currentCampaign) {
        if (!currentCampaign.isEmpty()) {
            if (tweetModel.getPayload().equals(currentCampaign.get(0).getCampaign().getPhrase())) {
                if (tweetModel.getTimestamp().isAfter(currentCampaign.get(0).getTimestamp())) {   
                    if (userFound.isEmpty()) {
                        throw new AppException(HttpErrors.BAD_REQUEST.getCode(), Errors.USER_NOT_FOUND);
                    }
                } else {
                    throw new AppException(HttpErrors.BAD_REQUEST.getCode(), Errors.TWEETED_CAMPAIGN_IS_BEFORE);
                }
            } else {
                throw new AppException(HttpErrors.BAD_REQUEST.getCode(), Errors.TWEETED_CAMPAIGN_IS_NOT_CURRENT);
            }
        } else {
            throw new AppException(HttpErrors.BAD_REQUEST.getCode(), Errors.NO_ACTIVE_CAMPAIGNS);
        }
    }

    private void verifyCampaignPhrase(Campaign campaign) {
        List<CurrentCampaign> currentCampaign = this.findCurrentCampaign();
        if (!currentCampaign.isEmpty()) {
            if (campaign.getPhrase().equals(currentCampaign.get(0).getCampaign().getPhrase())) {
                throw new AppException(HttpErrors.BAD_REQUEST.getCode(), Errors.CAMPAIGN_IS_ALREADY_CURRENT);
            }
        }
    }

    private Campaign updateCampaignCurrentPhraseFound(NewCampaignModel request, Campaign campaignFound) {
        Campaign campaign = campaignFound;
        campaign.setPhrase(request.getNewPhrase());
        List<CurrentCampaign> currentCampaignList = this.findCurrentCampaigns(request.getCurrentPhrase());
        if (!currentCampaignList.isEmpty()) {
            this.updateUserFromTweet(request.getCurrentPhrase());
            this.updateCurrentCampaigns(campaign, currentCampaignList);
        }
        return driver.database().update(campaignFound.getId(), campaignFound).get(0);
    }

    private Tweet createTweet(TweetModel tweetModel, List<User> userFound) {
        User user = userFound.get(0);
        Tweet tweet = Tweet.from(tweetModel, user);
        if (this.findTweet(tweet).isEmpty()) {
            this.updateUserPoints(user, Operations.INCREMENT);
            return driver.database().create("tweet", tweet);
        } else {
            throw new AppException(HttpErrors.BAD_REQUEST.getCode(), Errors.EMAIL_ALREADY_PARTICIPATED);
        }
    }

    private void updateUserPoints(User user, Operations operation) {
        user.setPoints(Operations.INCREMENT.equals(operation) ? user.getPoints() + 10 : user.getPoints() - 10);
        driver.database().update(user.getId(), user);
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

    private boolean wasCurrentCampaign(Tweet tweet) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'");
        String formattedDateTime = tweet.getTimestamp().format(formatter);

        Map<String, String> params = new HashMap<>();
        params.put("timestamp", formattedDateTime);
        List<CurrentCampaign> previousCampaign = driver.database().query("SELECT * FROM currentCampaign WHERE timestamp <= $timestamp ORDER BY timestamp DESC LIMIT 1", params, CurrentCampaign.class).get(0).getResult();

        if (!previousCampaign.isEmpty()) {
            CurrentCampaign campaignWasCurrent = previousCampaign.get(0);
            return tweet.getPayload().equals(campaignWasCurrent.getCampaign().getPhrase());
        }
        return false;
    }
    
    private void updateCurrentCampaigns(Campaign campaign, List<CurrentCampaign> currentCampaignList) {
        for (CurrentCampaign currentCampaign : currentCampaignList) {
            currentCampaign.setCampaign(campaign);
            driver.database().update(currentCampaign.getId(), currentCampaign);
        }
    }

    private void updateUserFromTweet(String currentPhrase) {
        List<Tweet> tweetsFound = this.findTweetsByPayload(currentPhrase);
        if (!tweetsFound.isEmpty()) {
            for (Tweet tweet : tweetsFound) {
                if (this.wasCurrentCampaign(tweet)) {
                    List<User> userFound = this.findUser(tweet.getUser().getEmail());
                    if (!userFound.isEmpty()) {
                        User user = userFound.get(0);
                        if (user.getPoints() > 0) {
                            this.updateUserPoints(user, Operations.SUBTRACT);
                        }
                        tweet.setUser(user);
                        driver.database().update(tweet.getId(), tweet);
                    }
                }
            }
        }
    }
}
