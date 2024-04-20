package org.ls.tweetpoints.operations.user;

import org.ls.tweetpoints.data.entities.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {

    private User user;
}
