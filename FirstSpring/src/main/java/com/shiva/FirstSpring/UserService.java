package com.shiva.FirstSpring;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<User> userList = new ArrayList<>();

    public List<User> fetchAllUsers() {
        return userList;
    }

    public Optional<User> fetchUser(Long id) {
        return userList.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public List<User> addUser(User user) {
        userList.add(user);
        return userList;
    }

    public Optional<User>updateFirstName(Long id, User updatedUser) {
        return Optional.ofNullable(userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    return existingUser;
                }).orElse(null));
    }
}
