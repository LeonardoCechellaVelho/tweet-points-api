package org.ls.tweetpoints.data.entities;

import java.util.UUID;

import org.ls.tweetpoints.data.models.CampaignModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
public class Campaign {
    private UUID id;
    private String phrase;

    public static Campaign from(CampaignModel campaignModel) {
        return new Campaign(UUID.randomUUID(), campaignModel.getPhrase());
    }
}
