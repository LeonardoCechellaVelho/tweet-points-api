package org.ls.tweetpoints.data.entities;

import org.ls.tweetpoints.data.models.UserModel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
public class User {
    private String email;
    private Integer points;

    public static User from(UserModel userModel) {
        return new User(userModel.getEmail(), 0);
    }
}
