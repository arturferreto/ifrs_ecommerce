package com.ifrs.ecommerce.controllers;

import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.dtos.LoginUserDto;
import com.ifrs.ecommerce.dtos.RegisterUserDto;
import com.ifrs.ecommerce.models.User;
import com.ifrs.ecommerce.responses.LoginResponse;
import com.ifrs.ecommerce.services.AuthenticationService;
import com.ifrs.ecommerce.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<DefaultResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return DefaultResponse.build(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<DefaultResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return DefaultResponse.build(loginResponse, "Login successful.");
    }
}
