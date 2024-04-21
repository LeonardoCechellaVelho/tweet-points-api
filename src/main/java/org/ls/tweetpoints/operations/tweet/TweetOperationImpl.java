package org.ls.tweetpoints.operations.tweet;

import org.ls.tweetpoints.data.entities.Tweet;
import org.ls.tweetpoints.services.TweetService;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Path("/Tweet")
public class TweetOperationImpl implements TweetOperation {
    
    TweetService tweetService;

    @Override
    @Transactional
    public TweetResponse addTweet(Tweet request) {
        Tweet tweet = tweetService.addTweet(request);
        return TweetResponse.builder().tweet(tweet).build();
    }
}
