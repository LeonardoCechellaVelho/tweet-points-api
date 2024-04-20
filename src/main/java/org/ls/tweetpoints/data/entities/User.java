package org.ls.tweetpoints.data.entities;

import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "`USER`")
public class User extends PanacheEntityBase {
    @Id
    private UUID id;

    @Column(unique = true, columnDefinition = "VARCHAR(255)")
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Tweet> tweets;
}
