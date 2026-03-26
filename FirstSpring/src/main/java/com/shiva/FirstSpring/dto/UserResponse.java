package com.shiva.FirstSpring.dto;

import com.shiva.FirstSpring.Model.UserRole;
import lombok.Data;


@Data
public class UserResponse {
    private String Id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role = UserRole.CUSTOMER;
    private AddressDto address;
}
