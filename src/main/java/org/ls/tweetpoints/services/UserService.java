package org.ls.tweetpoints.services;

import org.ls.tweetpoints.data.entities.User;
import org.ls.tweetpoints.data.models.UserModel;

public interface UserService {
    User addUser(UserModel request);
    User getUser(UserModel request);
}
