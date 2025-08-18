package com.hit.jobandlogging.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hit.jobandlogging.base.RestData;
import com.hit.jobandlogging.domain.entity.User;
import com.hit.jobandlogging.exception.UnauthorizedException;
import com.hit.jobandlogging.service.impl.CustomUserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final MessageSource messageSource;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = extractToken(request);

        log.info("Request Method: {}", request.getMethod());
        log.info("Request URL: {}", request.getRequestURL().toString());
        log.info("JWT: {}", jwt);
        try {
            if (jwt != null && jwtProvider.isValidToken(jwt)) {
                String username = jwtProvider.extractUsername(jwt);
                UserPrincipal userPrincipal = customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal,null,userPrincipal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (UnauthorizedException ex) {
            String message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getOutputStream().write(objectMapper.writeValueAsBytes(RestData.error(message)));
            return;
        }
        filterChain.doFilter(request, response);


    }

    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
