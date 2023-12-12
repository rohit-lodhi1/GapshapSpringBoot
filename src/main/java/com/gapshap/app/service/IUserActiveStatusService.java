package com.gapshap.app.service;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

import com.gapshap.app.constants.UserStatus;
import com.gapshap.app.model.chat.UserActiveStatus;

public interface IUserActiveStatusService {

	

	ResponseEntity<?> getUserStatus(Long id);

	UserStatus updateUserStatus(String email, UserStatus online);
}
