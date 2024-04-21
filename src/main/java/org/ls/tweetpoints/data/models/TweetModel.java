package org.ls.tweetpoints.data.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TweetModel {
    private String payload;
    private LocalDateTime timestamp;
    private UserModel user;
}
