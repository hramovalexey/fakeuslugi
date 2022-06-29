package com.fakeuslugi.security.dao;

import com.fakeuslugi.seasonservice.dao.ProvidedService;
import com.fakeuslugi.security.Authority;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;

// @Table(name="user_table")
@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Customer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    private long id;

    @NonNull
    @Column(nullable = false)
    private Authority authority;

    public Collection<? extends GrantedAuthority> getAuthorities(){
        // Due to current convention one user may have only one authority
        HashSet<Authority> authorities = new HashSet<>(1);
        authorities.add(authority);
        return authorities;
    }

    @NonNull
    @Column(nullable = false)
    private String password;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(nullable = false)
    private String secondName;

    @Column(nullable = true)
    private String thirdName;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    @NonNull
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(nullable = false)
    private ZonedDateTime registrationTime;

    @Override
    public String getUsername() {
        return email;
    }

/*    @OneToMany(mappedBy = "user")
    private Collection<ProvidedService> providedService;*/



    // TODO tether
    /*@OneToMany(mappedBy = "user", fetch= FetchType.LAZY)
    private Collection<Portfolio> portfolios;*/

    // TODO delete?
   /* public String toString(){
        return String.format("userId = %d, authority = %s, password = secret, username = %s, accountNonExpired = %b, accountNonLocked = %b, credentialsNonExpired = %b, enabled = %b",
                getUserId(), getAuthority(), getUsername(), isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), isEnabled());
    }*/
}
