package com.shiva.FirstSpring.Controller;

import com.shiva.FirstSpring.Model.User;
import com.shiva.FirstSpring.Service.UserService;
import com.shiva.FirstSpring.dto.UserRequest;
import com.shiva.FirstSpring.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping("/api/users")
    public ResponseEntity<Optional<UserResponse>> createUser(@RequestBody UserRequest user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> updateUserFirstName(@PathVariable Long id, @RequestBody UserRequest user) {
        return userService.updateFirstName(id, user)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}
