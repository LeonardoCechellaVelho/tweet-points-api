package org.ls.tweetpoints.services;

import java.util.List;

import org.ls.tweetpoints.data.entities.Tweet;
import org.ls.tweetpoints.data.entities.User;
import org.ls.tweetpoints.data.models.UserModel;

public interface UserService {
    User addUser(UserModel request);
    User getUser(UserModel request);
    List<Tweet> getUserTweets(UserModel request);
}
