package org.ls.tweetpoints.services;

import java.util.UUID;

import org.ls.tweetpoints.data.models.CampaignModel;

public interface CampaignService {
    
    UUID addCampaign(CampaignModel campaignModel);
}
