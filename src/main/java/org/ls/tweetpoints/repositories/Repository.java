package org.ls.tweetpoints.repositories;

import org.ls.tweetpoints.data.entities.Campaign;
import org.ls.tweetpoints.data.entities.User;

public interface Repository {

    Campaign persistCampaign(Campaign campaign);
    User persistUser(User user);
}
