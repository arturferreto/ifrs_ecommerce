package com.ifrs.ecommerce.controllers;

import com.ifrs.ecommerce.models.User;
import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<DefaultResponse> allUsers() {
        List <User> users = userService.allUsers();

        return DefaultResponse.build(users);
    }

    @GetMapping("/me")
    public ResponseEntity<DefaultResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return DefaultResponse.build(currentUser);
    }
}
