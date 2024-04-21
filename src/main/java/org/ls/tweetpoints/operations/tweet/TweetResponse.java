package org.ls.tweetpoints.operations.tweet;

import org.ls.tweetpoints.data.entities.Tweet;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TweetResponse {

    private Tweet tweet;
}
