package com.ifrs.ecommerce.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifrs.ecommerce.responses.DefaultResponse;
import com.ifrs.ecommerce.responses.LoginResponse;
import com.ifrs.ecommerce.services.JwtService;

@RequestMapping("/token")
@RestController
public class TokenController {
	private final JwtService jwtService;

	public TokenController(JwtService jwtService) {
		this.jwtService = jwtService;
	}
	
	@GetMapping("/generate-token")
	public ResponseEntity<DefaultResponse> generateToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();

		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}

		String jwtToken = jwtService.generateToken(userDetails);

		LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

		return DefaultResponse.build(loginResponse, "Login successful.");
	}
}
