package org.ls.tweetpoints.services;

import org.ls.tweetpoints.data.entities.Campaign;

public interface CampaignService {
    Campaign addCampaign(Campaign request);
    Campaign setCurrentCampaign(Campaign request);
    Campaign getCurrentCampaign();
}
