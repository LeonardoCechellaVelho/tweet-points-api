package org.ls.tweetpoints.operations.campaign;

import org.ls.tweetpoints.data.entities.Campaign;
import org.ls.tweetpoints.services.CampaignService;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Path("/Campaign")
public class CampaignOperationImpl implements CampaignOperation {
    
    CampaignService campaignService;

    @Override
    @Transactional
    public CampaignResponse addCampaign(Campaign campaignModel) {
        Campaign campaign = campaignService.addCampaign(campaignModel);
        return CampaignResponse.builder().campaign(campaign).build();
    }
}
