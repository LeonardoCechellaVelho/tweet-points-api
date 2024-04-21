package org.ls.tweetpoints.repositories;

import org.ls.tweetpoints.data.entities.Campaign;
import org.ls.tweetpoints.data.entities.Tweet;
import org.ls.tweetpoints.data.entities.User;
import org.ls.tweetpoints.data.models.TweetModel;

public interface Repository {

    Campaign persistCampaign(Campaign campaign);
    Campaign setCurrentCampaign(Campaign campaign);
    User persistUser(User user);
    Tweet persistTweet(TweetModel tweet);
}
