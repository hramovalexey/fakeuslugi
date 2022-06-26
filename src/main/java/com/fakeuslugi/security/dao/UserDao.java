package com.fakeuslugi.security.dao;

import com.fakeuslugi.TestEntity;
import com.fakeuslugi.security.Authority;
import com.fakeuslugi.security.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Slf4j
public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    public User createUser(String authorityName, String password, String username, String email) {
        // TODO make static factory
        Authority authority = new Authority(authorityName);
        if (authority == null) {
            log.error("No such authority name found: " + authorityName);
            throw new IllegalArgumentException("No such authority name found: " + authorityName);
        }
        User user = new User(authority, password, username, email);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        sessionFactory.getCurrentSession().save(user);
        log.debug("New user created " + user.toString());
        return user;
    }

    public User findByUsername(String nameRequest) {
        Query<User> query = sessionFactory.getCurrentSession().createQuery("from User as u where u.username = :nameRequest");
        query.setParameter("nameRequest", nameRequest);
        User result = query.uniqueResult();
        return result;
    }
}
