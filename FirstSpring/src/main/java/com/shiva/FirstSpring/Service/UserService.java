package com.shiva.FirstSpring.Service;

import com.shiva.FirstSpring.Model.Address;
import com.shiva.FirstSpring.Model.User;
import com.shiva.FirstSpring.Repository.UserRepository;
import com.shiva.FirstSpring.dto.AddressDto;
import com.shiva.FirstSpring.dto.UserRequest;
import com.shiva.FirstSpring.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public Optional<UserResponse> addUser(UserRequest userrequest) {
        User user = new User();
        updateUserFromUserRequest(user, userrequest);
        return Optional.of(userRepository.save(user)).map(this::mapToUserResponse);
    }

    public Optional<UserResponse>updateFirstName(Long id, UserRequest updatedUserequest) {
        User updatedUser = new User();
        updateUserFromUserRequest(updatedUser, updatedUserequest);
        return Optional.ofNullable(userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    userRepository.save(existingUser);
                    return existingUser;
                }).orElse(null)).map(this::mapToUserResponse);
    }

    public void updateUserFromUserRequest(User user, UserRequest userRequest)
    {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if(userRequest.getAddress() != null)
        {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setCountry(userRequest.getAddress().getCountry());

            user.setAddress(address);
        }
    }

    public UserResponse mapToUserResponse(User user)
    {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if(user.getAddress() != null) {
            Address address = user.getAddress();
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(address.getStreet());
            addressDto.setCity(address.getCity());
            addressDto.setState(address.getState());
            addressDto.setCountry(address.getCountry());
            addressDto.setZipcode(address.getZipcode());
            response.setAddress(addressDto);
        }
        return response;
    }
}
