package org.ls.tweetpoints.data.entities;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private UUID id;
    private String email;
    private List<Tweet> tweets;
}
