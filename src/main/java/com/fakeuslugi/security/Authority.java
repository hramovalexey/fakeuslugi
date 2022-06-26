package com.fakeuslugi.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Authority implements GrantedAuthority {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    private String authority;

    public Authority(String authority){
        this.authority = authority;
    }
}
