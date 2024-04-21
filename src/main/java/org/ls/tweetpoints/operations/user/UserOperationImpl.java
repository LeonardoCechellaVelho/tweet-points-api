package org.ls.tweetpoints.operations.user;

import org.ls.tweetpoints.data.entities.User;
import org.ls.tweetpoints.data.models.UserModel;
import org.ls.tweetpoints.services.UserService;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Path("/User")
public class UserOperationImpl implements UserOperation {
    
    UserService userService;

    @Override
    @Transactional
    public UserResponse addUser(UserModel request) {
        User user = userService.addUser(request);
        return UserResponse.builder().user(user).build();
    }
}
