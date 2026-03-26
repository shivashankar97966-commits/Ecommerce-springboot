package com.shiva.FirstSpring.Service;

import com.shiva.FirstSpring.Model.User;
import com.shiva.FirstSpring.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> fetchUser(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> addUser(User user) {
        return Optional.of(userRepository.save(user));
    }

    public Optional<User>updateFirstName(Long id, User updatedUser) {
        return Optional.ofNullable(userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    userRepository.save(existingUser);
                    return existingUser;
                }).orElse(null));
    }
}
