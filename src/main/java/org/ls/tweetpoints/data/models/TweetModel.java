package org.ls.tweetpoints.data.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TweetModel {
    private String payload;
    private LocalDateTime timestamp;
    private UserModel user;
}
