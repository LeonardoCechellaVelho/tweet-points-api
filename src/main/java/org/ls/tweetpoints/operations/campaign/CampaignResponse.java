package org.ls.tweetpoints.operations.campaign;

import org.ls.tweetpoints.data.entities.Campaign;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CampaignResponse {

    private Campaign campaign;
}
