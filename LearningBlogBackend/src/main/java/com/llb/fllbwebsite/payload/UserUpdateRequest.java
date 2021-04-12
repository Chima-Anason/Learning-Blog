package com.llb.fllbwebsite.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserUpdateRequest {

    @NotNull(message = "User id is required")
    private Long id;

    @NotBlank(message = "Field cannot be blank")
    private String firstName;

    @NotBlank(message = "Field cannot be blank")
    private String lastName;

    @NotBlank(message = "Field cannot be blank")
    private String username;

    @Email(message = "Please input a valid email address")
    @NotBlank(message = "Field cannot be blank")
    private String email;

    @NotBlank(message = "Field cannot be blank")
    @Size(min = 11, max = 11, message = "Invalid mobile number")
    private String phoneNumber;

    private String avatarImg;

    @NotBlank(message = "Field cannot be blank")
    private String roleName;
}
