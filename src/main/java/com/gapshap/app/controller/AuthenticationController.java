package com.gapshap.app.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gapshap.app.constants.AppConstants;
import com.gapshap.app.payload.LoginRequest;
import com.gapshap.app.payload.ResendOtpRequest;
import com.gapshap.app.payload.UserRegistrationRequest;
import com.gapshap.app.payload.VerificationRequest;
import com.gapshap.app.service.IAuthenticationService;

import jakarta.servlet.http.HttpServletResponse;

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
	
	@PostMapping("/resend")
	public ResponseEntity<?> resendOtp(@RequestBody ResendOtpRequest resendOtpRequest){
		 return authService.resendOtp(resendOtpRequest);
	}
	
	@GetMapping(value="/profileImage/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void getProfileImg(HttpServletResponse response,@PathVariable String imageName) {
		try {
			InputStream imageFile = new FileInputStream(AppConstants.PROFILE_PATH +imageName);
			
			StreamUtils.copy(imageFile, response.getOutputStream());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
