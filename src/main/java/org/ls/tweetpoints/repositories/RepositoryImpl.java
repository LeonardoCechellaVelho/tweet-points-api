package org.ls.tweetpoints.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ls.tweetpoints.config.AppException;
import org.ls.tweetpoints.config.SurrealConfig;
import org.ls.tweetpoints.data.entities.Campaign;
import org.ls.tweetpoints.data.entities.User;
import org.ls.tweetpoints.data.enums.Errors;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class RepositoryImpl implements Repository {

    private SurrealConfig driver;

    @Override
    public Campaign persistCampaign(Campaign campaign) {
        return driver.database().create("campaign", campaign);
    }

    @Override
    public User persistUser(User user) {
        Map<String, String> params = new HashMap<>();
        params.put("email", user.getEmail());
        System.out.println(driver.database().query("SELECT * FROM user WHERE email = $email", params, User.class).get(0).getResult());
        if (driver.database().query("SELECT * FROM user WHERE email = $email", params, User.class).get(0).getResult().isEmpty()) {
            return driver.database().create("user", user);
        } else {
            throw new AppException(Errors.BAD_REQUEST.getCode(),"E-mail already registered");
        }
    }

    public List<Campaign> getAllCampaigns() {
        return driver.database().select("campaign", Campaign.class);
    }
}
