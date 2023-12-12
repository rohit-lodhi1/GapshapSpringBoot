package com.gapshap.app.service;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

import com.gapshap.app.payload.PageReqst;
import com.gapshap.app.payload.UserRequest;

public interface IUserService {

	ResponseEntity<?> getAllUsers(PageReqst pageRequest);
	
	ResponseEntity<?> getUserById(Long userId);
	
	ResponseEntity<?> getUserByEmailOrUserName(String value);

	ResponseEntity<?> updateUser(UserRequest request, Principal p);
}
