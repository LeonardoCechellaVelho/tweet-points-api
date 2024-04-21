package org.ls.tweetpoints.operations.user;

import java.util.List;

import org.ls.tweetpoints.data.entities.Tweet;
import org.ls.tweetpoints.data.entities.User;
import org.ls.tweetpoints.data.models.UserModel;
import org.ls.tweetpoints.services.UserService;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Path("/user")
public class UserOperationImpl implements UserOperation {
    
    UserService userService;

    @Override
    @Transactional
    public UserResponse addUser(UserModel request) {
        User user = userService.addUser(request);
        return UserResponse.builder().user(user).build();
    }

    @Override
    @Transactional
    public UserResponse getUser(String email) {
        User user = userService.getUser(new UserModel(email));
        return UserResponse.builder().user(user).build();
    }

    @Override
    @Transactional
    public UserTweetsResponse getUserTweets(String email) {
        List<Tweet> tweets = userService.getUserTweets(new UserModel(email));
        return UserTweetsResponse.builder().tweets(tweets).build();
    }
}
