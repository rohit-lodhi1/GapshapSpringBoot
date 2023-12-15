package com.gapshap.app.service;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

import com.gapshap.app.constants.UserStatus;
import com.gapshap.app.model.chat.UserActiveStatus;
import com.gapshap.app.payload.UserStatusRequest;

public interface IUserActiveStatusService {

	

	ResponseEntity<?> getUserStatus(Long id);

	UserStatus updateUserStatus(UserStatusRequest request);
}
