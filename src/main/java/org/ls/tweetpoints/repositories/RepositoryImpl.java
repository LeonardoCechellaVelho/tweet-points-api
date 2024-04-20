package org.ls.tweetpoints.repositories;

import java.util.UUID;

import org.ls.tweetpoints.data.entities.Campaign;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RepositoryImpl implements Repository {
    
    public UUID persistCampaign(Campaign campaign) {
        campaign.persist();
        return campaign.getId();
    };
}
