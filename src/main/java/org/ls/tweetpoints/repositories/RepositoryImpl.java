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
    public Tweet persistTweet(TweetModel tweetModel) {
        List<User> userFound = this.findUser(tweetModel.getUser().getEmail());
        if (!this.findCampaign(tweetModel.getPayload()).isEmpty()) {
            if (!userFound.isEmpty()) {
                User user = userFound.get(0);
                Tweet tweet = Tweet.from(tweetModel, user);
                if (this.findTweet(tweet).isEmpty()) {
                    user.setPoints(user.getPoints() + 10);
                    driver.database().update("user", user);
                    return driver.database().create("tweet", tweet);
                } else {
                    throw new AppException(Errors.BAD_REQUEST.getCode(),"E-mail already has participated in this campaign");
                }
            } else {
                throw new AppException(Errors.BAD_REQUEST.getCode(),"User not found");
            }
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"Campaign does not exists");
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

    private List<Campaign> findCampaign(String phrase) {
        Map<String, String> params = new HashMap<>();
        params.put("phrase", phrase);
        return driver.database().query("SELECT * FROM campaign WHERE phrase = $phrase", params, Campaign.class).get(0).getResult();
    }
}
