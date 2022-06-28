package com.fakeuslugi.controller.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserModel {
    private String username;
    private String password;
    private String email; // TODO validate

}
