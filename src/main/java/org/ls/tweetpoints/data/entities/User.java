package org.ls.tweetpoints.data.entities;

import org.ls.tweetpoints.data.models.UserModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
public class User {
    private String id;
    private String email;
    private Integer points;

    public static User from(UserModel userModel) {
        return new User(null, userModel.getEmail(), 0);
    }
}
