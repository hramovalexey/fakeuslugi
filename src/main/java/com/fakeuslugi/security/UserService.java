package com.fakeuslugi.security;

import com.fakeuslugi.security.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails createUser(String authorityName, String password, String username, String email){
        password = passwordEncoder.encode(password);
        UserDetails newUser = userDao.createUser(authorityName, password, username, email);
        return newUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            log.warn("No such user reigstered: " + username);
            throw new UsernameNotFoundException("No such user registered!");
        }
        return user;
    }
}
