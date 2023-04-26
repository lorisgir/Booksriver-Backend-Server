package com.ghibo.userserver.controllers;


import com.ghibo.userserver.configuration.security.JwtTokenUtil;
import com.ghibo.userserver.configuration.security.services.UserDetailsImpl;
import com.ghibo.userserver.domain.dto.UserView;
import com.ghibo.userserver.domain.dto.requests.AuthRequest;
import com.ghibo.userserver.domain.dto.requests.CreateUserRequest;
import com.ghibo.userserver.domain.dto.requests.LoginSocialRequest;
import com.ghibo.userserver.domain.mapper.UserViewMapper;
import com.ghibo.userserver.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Authentication")
@RestController
@RequestMapping(path = "auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    UserViewMapper userViewMapper = Mappers.getMapper(UserViewMapper.class);

    @PostMapping("login")
    public ResponseEntity<UserView> login(@RequestBody @Valid AuthRequest request) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(userDetails.getId()))
                .body(userViewMapper.toUserView(userDetails));


    }

    @PostMapping("register")
    public ResponseEntity<UserView> register(@RequestBody @Valid CreateUserRequest request) {
        UserView userView = userService.create(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(userView.getId()))
                .body(userView);
    }

    @PostMapping("login-social")
    public ResponseEntity<UserView> loginSocial(@RequestBody @Valid LoginSocialRequest request) {
        UserView userView = userService.loginSocial(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(userView.getId()))
                .body(userView);


    }


}
