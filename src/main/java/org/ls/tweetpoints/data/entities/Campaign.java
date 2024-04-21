package org.ls.tweetpoints.data.entities;

import org.ls.tweetpoints.data.models.CampaignModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class Campaign {
    private String id;
    private String phrase;

    public static Campaign from(CampaignModel campaignModel) {
        return new Campaign(null, campaignModel.getPhrase());
    }
}
