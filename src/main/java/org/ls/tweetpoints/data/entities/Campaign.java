package org.ls.tweetpoints.data.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Campaign {
    private String phrase;
    private boolean inCampaign;
}
