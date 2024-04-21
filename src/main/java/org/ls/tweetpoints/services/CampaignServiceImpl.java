package org.ls.tweetpoints.services;

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
    public Campaign addCampaign(Campaign request) {
        return this.repository.persistCampaign(request);
    };

    @Override
    public Campaign setCurrentCampaign(Campaign request) {
        return this.repository.setCurrentCampaign(request);
    };

    @Override
    public Campaign updateCampaign(CampaignModel request) {
        return this.repository.updateCampaign(request);
    };

    @Override
    public Campaign getCurrentCampaign() {
        return this.repository.getCurrentCampaign();
    };
}
