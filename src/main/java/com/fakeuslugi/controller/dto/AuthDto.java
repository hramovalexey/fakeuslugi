package com.fakeuslugi.controller.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AuthDto {

    @NotBlank(message = "email is required")
    @Email(message = "invalid email")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

}
