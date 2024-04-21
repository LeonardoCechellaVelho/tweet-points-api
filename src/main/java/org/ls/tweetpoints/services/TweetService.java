package org.ls.tweetpoints.services;

import org.ls.tweetpoints.data.entities.Tweet;
import org.ls.tweetpoints.data.models.TweetModel;

public interface TweetService {
    Tweet addTweet(TweetModel request);
}
