package org.ls.tweetpoints.services;

import org.ls.tweetpoints.data.entities.Campaign;
import org.ls.tweetpoints.data.models.CampaignModel;

public interface CampaignService {
    Campaign addCampaign(Campaign request);
    Campaign setCurrentCampaign(Campaign request);
    Campaign updateCampaign(CampaignModel request);
    Campaign getCurrentCampaign();
}
