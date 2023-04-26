package com.ghibo.bookserver.configuration.security;

import com.ghibo.bookserver.configuration.SessionParam;
import com.ghibo.bookserver.configuration.feign.AuthFeignClient;
import com.ghibo.bookserver.configuration.security.services.UserDetailsImpl;
import com.ghibo.bookserver.domain.models.User;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.EntityManager;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    AuthFeignClient authFeignClient;

    @Autowired
    EntityManager entityManager;

    @Autowired
    SessionParam sessionParam;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || header.isBlank() || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Synchronous request to User Microservice to get current user
        UserDetailsImpl userDetails = UserDetailsImpl.build(authFeignClient.getMe());

        sessionParam.setUid(userDetails.getId());

        // Set user identity on the spring security context
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, null
        );

        authentication
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
