package com.fakeuslugi.controller.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CustomerDto {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "second name is required")
    private String secondName;

    private String thirdName;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email")
    private String email;

}
