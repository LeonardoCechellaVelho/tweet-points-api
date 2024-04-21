package org.ls.tweetpoints.services;

import java.util.List;

import org.ls.tweetpoints.data.entities.Tweet;
import org.ls.tweetpoints.data.entities.User;
import org.ls.tweetpoints.data.models.UserModel;
import org.ls.tweetpoints.repositories.Repository;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class UserServiceImpl implements UserService {

    private final Repository repository;
    private ValidatorService validator;

    @Override
    public User addUser(UserModel request) {
        validator.validateUser(request);
        return this.repository.persistUser(User.from(request));
    }
    
    @Override
    public User getUser(UserModel request) {
        validator.validateUser(request);
        return this.repository.getUser(request);
    }
    
    @Override
    public List<Tweet> getUserTweets(UserModel request) {
        validator.validateUser(request);
        return this.repository.getUserTweets(request);
    }
}
