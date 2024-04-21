package org.ls.tweetpoints.services;

import org.ls.tweetpoints.data.models.CampaignModel;
import org.ls.tweetpoints.data.models.NewCampaignModel;
import org.ls.tweetpoints.data.models.TweetModel;
import org.ls.tweetpoints.data.models.UserModel;

public interface ValidatorService {
    void validateUser(UserModel user);
    void validateTweet(TweetModel tweet);
    void validateCampaign(NewCampaignModel campaign);
    void validateCampaign(CampaignModel campaign);
}
