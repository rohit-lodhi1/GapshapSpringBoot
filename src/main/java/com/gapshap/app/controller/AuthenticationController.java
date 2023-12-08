package com.gapshap.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gapshap.app.payload.LoginRequest;
import com.gapshap.app.payload.UserRegistrationRequest;
import com.gapshap.app.payload.VerificationRequest;
import com.gapshap.app.service.IAuthenticationService;

@RestController
@RequestMapping("/gapshap/auth")
@CrossOrigin("*")
public class AuthenticationController {

	@Autowired
	private IAuthenticationService authService;
	
	@PostMapping("/")
	public ResponseEntity<?> registration(@RequestBody UserRegistrationRequest request){
		 return authService.registration(request);
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verifyOtp(@RequestBody VerificationRequest request){
		return authService.verifyUser(request);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request){
		return authService.loginUser(request);
	}
	
	@GetMapping("/current-user")
	public ResponseEntity<?> getCurrentUser(Principal p){
		return authService.getCurrentUser(p);
	}
}
