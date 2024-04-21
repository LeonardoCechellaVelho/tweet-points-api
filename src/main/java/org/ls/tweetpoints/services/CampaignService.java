package org.ls.tweetpoints.services;

import org.ls.tweetpoints.data.entities.Campaign;
import org.ls.tweetpoints.data.models.CampaignModel;
import org.ls.tweetpoints.data.models.NewCampaignModel;

public interface CampaignService {
    Campaign addCampaign(CampaignModel request);
    Campaign setCurrentCampaign(CampaignModel request);
    Campaign updateCampaign(NewCampaignModel request);
    Campaign getCurrentCampaign();
}
