package org.ls.tweetpoints.data.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Tweet {
    private UUID id;
    private String payload;
    private LocalDateTime timestamp;
    private User user;
}
