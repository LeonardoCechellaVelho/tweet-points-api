package org.ls.tweetpoints.data.entities;

import java.util.UUID;

import org.ls.tweetpoints.data.models.CampaignModel;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
@Entity
@Table(name = "CAMPAIGN")
public class Campaign extends PanacheEntityBase {
    @Id
    private UUID id;

    private String phrase;

    public static Campaign from(CampaignModel campaignModel) {
        return new Campaign(UUID.randomUUID(), campaignModel.getPhrase());
    }
}
