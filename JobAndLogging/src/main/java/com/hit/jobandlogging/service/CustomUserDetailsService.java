package com.hit.jobandlogging.service;

import com.hit.jobandlogging.security.UserPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService {
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException;
    public UserPrincipal loadUserByEmail(String email) throws UsernameNotFoundException;
}
