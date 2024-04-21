package org.ls.tweetpoints.operations.campaign;

import org.ls.tweetpoints.data.entities.Campaign;
import org.ls.tweetpoints.data.models.CampaignModel;
import org.ls.tweetpoints.data.models.NewCampaignModel;
import org.ls.tweetpoints.services.CampaignService;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Path("/campaign")
public class CampaignOperationImpl implements CampaignOperation {
    
    CampaignService campaignService;

    @Override
    @Transactional
    public CampaignResponse addCampaign(CampaignModel request) {
        Campaign campaign = campaignService.addCampaign(request);
        return CampaignResponse.builder().campaign(campaign).build();
    }

    @Override
    @Transactional
    public CampaignResponse setCurrentCampaign(CampaignModel request) {
        Campaign campaign = campaignService.setCurrentCampaign(request);
        return CampaignResponse.builder().campaign(campaign).build();
    }

    @Override
    @Transactional
    public CampaignResponse updateCampaign(NewCampaignModel request) {
        Campaign campaign = campaignService.updateCampaign(request);
        return CampaignResponse.builder().campaign(campaign).build();
    }

    @Override
    @Transactional
    public CampaignResponse getCurrentCampaign() {
        Campaign campaign = campaignService.getCurrentCampaign();
        return CampaignResponse.builder().campaign(campaign).build();
    }
}
