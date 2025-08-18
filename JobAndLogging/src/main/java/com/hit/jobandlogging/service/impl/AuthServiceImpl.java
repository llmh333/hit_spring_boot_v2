package com.hit.jobandlogging.service.impl;

import com.hit.jobandlogging.constant.ErrorMessage;
import com.hit.jobandlogging.constant.RoleConstant;
import com.hit.jobandlogging.domain.dto.request.LoginRequestDto;
import com.hit.jobandlogging.domain.dto.request.LogoutRequestDto;
import com.hit.jobandlogging.domain.dto.request.RegisterRequestDto;
import com.hit.jobandlogging.domain.dto.response.LoginResponseDto;
import com.hit.jobandlogging.domain.dto.response.UserResponseDto;
import com.hit.jobandlogging.domain.entity.Role;
import com.hit.jobandlogging.domain.entity.User;
import com.hit.jobandlogging.domain.mapper.UserMapper;
import com.hit.jobandlogging.exception.ConflictException;
import com.hit.jobandlogging.exception.InternalServerErrorException;
import com.hit.jobandlogging.exception.NotFoundException;
import com.hit.jobandlogging.exception.UnauthorizedException;
import com.hit.jobandlogging.repository.RoleRepository;
import com.hit.jobandlogging.repository.UserRepository;
import com.hit.jobandlogging.security.JwtProvider;
import com.hit.jobandlogging.security.UserPrincipal;
import com.hit.jobandlogging.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenBlackListServiceImpl tokenBlackListService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(RegisterRequestDto registerRequestDto) {
        log.info("Register request received");
        if (userRepository.existsUserByUsername(registerRequestDto.getUsername())) {
            throw new ConflictException(ErrorMessage.User.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsUserByEmail(registerRequestDto.getEmail())) {
            throw new ConflictException(ErrorMessage.User.EMAIL_ALREADY_EXISTS);
        }

        Role role = roleRepository.findByName(RoleConstant.USER);
        if (role == null) {
            throw new NotFoundException(ErrorMessage.Role.ROLE_NOT_FOUND, new String[]{RoleConstant.USER.name()});
        }
        User user = userMapper.toUser(registerRequestDto);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        return userMapper.toUserResponseDto(userRepository.save(user));
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        log.info("Start logging into {} account",  loginRequestDto.getUsername());
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String accessToken = jwtProvider.generateToken(userPrincipal, false);
            String refreshToken = jwtProvider.generateToken(userPrincipal, true);
            log.info("Logged in into {} account",  userPrincipal.getUsername());
            return LoginResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (InternalAuthenticationServiceException e){
            throw new UnauthorizedException(ErrorMessage.Auth.USERNAME_OR_PASSWORD_INCORRECT);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(ErrorMessage.Auth.USERNAME_OR_PASSWORD_INCORRECT);
        } catch (Exception e) {
            throw new InternalServerErrorException(ErrorMessage.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean logout(LogoutRequestDto logoutRequestDto) {
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContextHolder.clearContext();
        tokenBlackListService.addTokenBlackList(logoutRequestDto.getAccessToken());
        tokenBlackListService.addTokenBlackList(logoutRequestDto.getRefreshToken());
        return true;
    }
}
