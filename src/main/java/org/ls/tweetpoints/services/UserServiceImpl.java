package org.ls.tweetpoints.services;

import org.ls.tweetpoints.data.entities.User;
import org.ls.tweetpoints.repositories.Repository;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class UserServiceImpl implements UserService {

    private final Repository repository;

    @Override
    public User addUser(User request) {
        return this.repository.persistUser(request);
    }
    
}
