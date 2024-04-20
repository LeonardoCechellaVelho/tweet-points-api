package org.ls.tweetpoints.repositories;

import java.util.UUID;

import org.ls.tweetpoints.data.entities.Campaign;

public interface Repository {

    UUID persistCampaign(Campaign campaign);
    
}
