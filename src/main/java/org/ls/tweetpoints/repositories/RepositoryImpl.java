package org.ls.tweetpoints.repositories;

import java.util.UUID;

import org.ls.tweetpoints.data.entities.Campaign;

import com.surrealdb.driver.SyncSurrealDriver;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class RepositoryImpl implements Repository {

    private SyncSurrealDriver driver;

    @Override
    public void persistCampaign(Campaign campaign) {
        System.out.println(driver.create("campaign", campaign).toString());
    }

    /* public List<Campaign> getAllCampaigns() {
        return driver.select("campaign", Campaign.class);
    } */
}
