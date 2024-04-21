package org.ls.tweetpoints.services;

import org.ls.tweetpoints.data.entities.Campaign;
import org.ls.tweetpoints.data.models.CampaignModel;
import org.ls.tweetpoints.data.models.NewCampaignModel;
import org.ls.tweetpoints.repositories.Repository;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class CampaignServiceImpl implements CampaignService {

    private final Repository repository;
    private ValidatorService validator;
    
    @Override
    public Campaign addCampaign(CampaignModel request) {
        validator.validateCampaign(request);
        return this.repository.persistCampaign(Campaign.from(request));
    };

    @Override
    public Campaign setCurrentCampaign(CampaignModel request) {
        validator.validateCampaign(request);
        return this.repository.setCurrentCampaign(Campaign.from(request));
    };

    @Override
    public Campaign updateCampaign(NewCampaignModel request) {
        validator.validateCampaign(request);
        return this.repository.updateCampaign(request);
    };

    @Override
    public Campaign getCurrentCampaign() {
        return this.repository.getCurrentCampaign();
    };
}
