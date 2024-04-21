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
    public CampaignResponse addCampaign(Campaign request) {
        Campaign campaign = campaignService.addCampaign(request);
        return CampaignResponse.builder().campaign(campaign).build();
    }

    @Override
    @Transactional
    public CampaignResponse setCurrentCampaign(Campaign request) {
        Campaign campaign = campaignService.setCurrentCampaign(request);
        return CampaignResponse.builder().campaign(campaign).build();
    }

    @Override
    @Transactional
    public CampaignResponse getCurrentCampaign() {
        Campaign campaign = campaignService.getCurrentCampaign();
        return CampaignResponse.builder().campaign(campaign).build();
    }
}
