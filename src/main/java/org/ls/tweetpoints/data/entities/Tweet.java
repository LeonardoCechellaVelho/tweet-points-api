package org.ls.tweetpoints.data.entities;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Tweet {
    private String payload;
    private LocalDateTime timestamp;
    private User user;
}
