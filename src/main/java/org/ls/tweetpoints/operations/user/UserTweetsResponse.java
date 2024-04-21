package org.ls.tweetpoints.operations.user;

import java.util.List;

import org.ls.tweetpoints.data.entities.Tweet;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserTweetsResponse {
    private List<Tweet> tweets;
}
