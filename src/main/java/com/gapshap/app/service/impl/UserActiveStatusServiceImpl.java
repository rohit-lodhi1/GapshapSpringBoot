package com.gapshap.app.service.impl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gapshap.app.constants.AppConstants;
import com.gapshap.app.constants.UserStatus;
import com.gapshap.app.exception.UserNotFoundException;
import com.gapshap.app.model.User;
import com.gapshap.app.model.chat.UserActiveStatus;
import com.gapshap.app.payload.UserStatusRequest;
import com.gapshap.app.repository.UserActiveStatusRepository;
import com.gapshap.app.repository.UserRepository;
import com.gapshap.app.service.IUserActiveStatusService;


@Service
public class UserActiveStatusServiceImpl implements IUserActiveStatusService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserActiveStatusRepository activeStatusRepository;
	
	@Override
	public UserStatus updateUserStatus(UserStatusRequest request) {
		
		System.out.println(request.getEmail());
	User user = this.userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
	UserActiveStatus userStatus = user.getUserStatus();
	if(request.getStatus().equals(UserStatus.ONLINE.toString()))
	   userStatus.setStatus(UserStatus.ONLINE);
	else if(request.getStatus().equals(UserStatus.TYPING.toString())) {
		userStatus.setStatus(UserStatus.TYPING);
	}
	else
		userStatus.setStatus(UserStatus.OFFLINE);
	userStatus.setLastSeen(LocalDateTime.now());
	  this.activeStatusRepository.save(userStatus);
   return userStatus.getStatus();
	}

	@Override
	public ResponseEntity<?> getUserStatus(Long id) {
		Map<String, Object> response = new HashMap<>();
		User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		response.put(AppConstants.MESSAGE, AppConstants.STATUS_RETERIVED);
		response.put(AppConstants.DATA_MESSAGE, user.getUserStatus());
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

}
