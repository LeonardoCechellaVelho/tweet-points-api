package org.ls.tweetpoints.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ls.tweetpoints.config.AppException;
import org.ls.tweetpoints.config.SurrealConfig;
import org.ls.tweetpoints.data.entities.Campaign;
import org.ls.tweetpoints.data.entities.Tweet;
import org.ls.tweetpoints.data.entities.User;
import org.ls.tweetpoints.data.enums.Errors;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class RepositoryImpl implements Repository {

    private SurrealConfig driver;

    @Override
    public Campaign persistCampaign(Campaign campaign) {
        Map<String, String> params = new HashMap<>();
        params.put("phrase", campaign.getPhrase());
        if (driver.database().query("SELECT * FROM campaign WHERE phrase = $phrase", params, Campaign.class).get(0).getResult().isEmpty()) {
            return driver.database().create("campaign", campaign);
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"Phrase already registered");
        }
    }

    @Override
    public Tweet persistTweet(Tweet tweet) {
        Map<String, String> params = new HashMap<>();
        params.put("email", tweet.getUser().getEmail());
        params.put("payload", tweet.getPayload());
        if (driver.database().query("SELECT * FROM tweet WHERE user.email = $email and payload = $payload", params, Tweet.class).get(0).getResult().isEmpty()) {
            return driver.database().create("tweet", tweet);
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"E-mail already has participated in this campaign");
        }
    }

    @Override
    public User persistUser(User user) {
        Map<String, String> params = new HashMap<>();
        params.put("email", user.getEmail());
        if (driver.database().query("SELECT * FROM user WHERE email = $email", params, User.class).get(0).getResult().isEmpty()) {
            return driver.database().create("user", user);
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"E-mail already registered");
        }
    }

    /* public List<Campaign> getAllCampaigns(String email, LocalDateTime timeFrom, LocalDateTime timeTo) {
        Map<String, String> params = new HashMap<>();
        params.put("email", tweet.getUser().getEmail());
        params.put("payload", tweet.getPayload());
        driver.database().query("SELECT * FROM Tweet WHERE email = $email", params, User.class).get(0).getResult()
    } */
}
