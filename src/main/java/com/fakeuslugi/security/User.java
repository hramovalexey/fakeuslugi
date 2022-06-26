package com.fakeuslugi.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name="user_table")
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    private long userId;

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
    @Column(nullable = false, unique = true)
    private String username;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    @NonNull
    @Column(nullable = false)
    private String email;

    // TODO tether
    /*@OneToMany(mappedBy = "user", fetch= FetchType.LAZY)
    private Collection<Portfolio> portfolios;*/

    // TODO delete?
   /* public String toString(){
        return String.format("userId = %d, authority = %s, password = secret, username = %s, accountNonExpired = %b, accountNonLocked = %b, credentialsNonExpired = %b, enabled = %b",
                getUserId(), getAuthority(), getUsername(), isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), isEnabled());
    }*/
}
