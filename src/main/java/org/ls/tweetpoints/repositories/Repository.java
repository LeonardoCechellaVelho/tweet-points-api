package org.ls.tweetpoints.repositories;

import org.ls.tweetpoints.data.entities.Campaign;
import org.ls.tweetpoints.data.entities.Tweet;
import org.ls.tweetpoints.data.entities.User;
import org.ls.tweetpoints.data.models.TweetModel;
import org.ls.tweetpoints.data.models.UserModel;

public interface Repository {

    Campaign persistCampaign(Campaign campaign);
    Campaign setCurrentCampaign(Campaign campaign);
    Campaign getCurrentCampaign();
    User persistUser(User user);
    User getUser(UserModel userModel);
    Tweet persistTweet(TweetModel tweet);
}
