package com.hit.jobandlogging.security;

import com.hit.jobandlogging.constant.Gender;
import com.hit.jobandlogging.domain.entity.Role;
import com.hit.jobandlogging.domain.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserPrincipal implements UserDetails {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String username, String password, String firstName, String lastName, String email, Gender gender, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.authorities = authorities == null ? null : new ArrayList<>(authorities);
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName().name())));

        return new UserPrincipal(
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getGender(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
