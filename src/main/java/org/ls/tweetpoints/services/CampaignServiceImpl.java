package org.ls.tweetpoints.services;

import java.util.UUID;

import org.ls.tweetpoints.data.entities.Campaign;
import org.ls.tweetpoints.data.models.CampaignModel;
import org.ls.tweetpoints.repositories.Repository;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class CampaignServiceImpl implements CampaignService {

    private final Repository repository;
    
    @Override
    public UUID addCampaign(CampaignModel campaignModel) {
        return this.repository.persistCampaign(Campaign.from(campaignModel));
    };
}
