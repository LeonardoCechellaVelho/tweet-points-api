package org.ls.tweetpoints.services;

import org.ls.tweetpoints.data.entities.Tweet;
import org.ls.tweetpoints.data.models.TweetModel;
import org.ls.tweetpoints.repositories.Repository;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class TweetServiceImpl implements TweetService {

    private final Repository repository;

    @Override
    public Tweet addTweet(TweetModel request) {
        return this.repository.persistTweet(request);
    }
    
}
