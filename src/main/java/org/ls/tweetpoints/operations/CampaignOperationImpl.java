package org.ls.tweetpoints.operations;

import org.ls.tweetpoints.data.models.CampaignModel;
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
    public CampaignResponse addCampaign(CampaignModel campaignModel) {
        campaignService.addCampaign(campaignModel);
        return CampaignResponse.builder().sucesso(true).build();
    }
}
