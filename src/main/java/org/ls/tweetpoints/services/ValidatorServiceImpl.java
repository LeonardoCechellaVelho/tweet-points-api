package org.ls.tweetpoints.services;

import org.ls.tweetpoints.data.models.CampaignModel;
import org.ls.tweetpoints.data.models.NewCampaignModel;
import org.ls.tweetpoints.data.models.TweetModel;
import org.ls.tweetpoints.data.models.UserModel;
import org.ls.tweetpoints.validators.EmailValidator;
import org.ls.tweetpoints.validators.EmptyValidator;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class ValidatorServiceImpl implements ValidatorService {

    @Override
    public void validateUser(UserModel user) {
        EmailValidator.isValid(user.getEmail());
    }

    @Override
    public void validateTweet(TweetModel tweet) {
        EmptyValidator.isValid(tweet.getUser(), "User");
        EmptyValidator.isValid(tweet.getPayload(), "Payload");
        EmailValidator.isValid(tweet.getUser().getEmail());
    }

    @Override
    public void validateCampaign(NewCampaignModel campaign) {
        EmptyValidator.isValid(campaign.getCurrentPhrase(), "CurrentPhrase");
        EmptyValidator.isValid(campaign.getNewPhrase(), "NewPhrase");
    }
    
    @Override
    public void validateCampaign(CampaignModel campaign) {
        EmptyValidator.isValid(campaign.getPhrase(), "Phrase");
    }
    
}
