package org.ls.tweetpoints.data.entities;

import java.time.LocalDateTime;

import org.ls.tweetpoints.data.models.TweetModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
public class Tweet {
    private String id;
    private String payload;
    private LocalDateTime timestamp;
    private User user;

    
    public static Tweet from(TweetModel tweetModel, User user) {
        return new Tweet(null, tweetModel.getPayload(), tweetModel.getTimestamp(), user);
    }
}
