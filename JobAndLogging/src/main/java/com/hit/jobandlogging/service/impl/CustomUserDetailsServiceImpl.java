package com.hit.jobandlogging.service.impl;

import com.hit.jobandlogging.constant.ErrorMessage;
import com.hit.jobandlogging.domain.entity.User;
import com.hit.jobandlogging.exception.NotFoundException;
import com.hit.jobandlogging.repository.UserRepository;
import com.hit.jobandlogging.security.UserPrincipal;
import com.hit.jobandlogging.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new NotFoundException(ErrorMessage.User.USERNAME_NOT_FOUND, new String[]{username});
        }
        return UserPrincipal.create(userOptional.get());
    }

    @Override
    public UserPrincipal loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(email);
        if (userOptional.isEmpty()) {
            throw new NotFoundException(ErrorMessage.User.EMAIL_NOT_FOUND, new String[]{email});
        }
        return UserPrincipal.create(userOptional.get());
    }
}
