package com.gapshap.app.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gapshap.app.constants.AppConstants;
import com.gapshap.app.constants.UserStatus;
import com.gapshap.app.model.chat.UserActiveStatus;
import com.gapshap.app.payload.PageReqst;
import com.gapshap.app.payload.UserRequest;
import com.gapshap.app.service.IUserActiveStatusService;
import com.gapshap.app.service.IUserService;

@RestController
@RequestMapping("/gapshap/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private SimpMessagingTemplate  messageTemplate; 
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserActiveStatusService statusService;
	
	@PostMapping("/")
	public ResponseEntity<?> getAllUsers(@RequestBody PageReqst reqst){
		return userService.getAllUsers(reqst);
	}
	

	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable Long userId){
		return userService.getUserById(userId);
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> getUserByEmailOrUserName(@RequestParam("value") String value){
		return userService.getUserByEmailOrUserName(value);
	}
	
	@PutMapping("/")
	public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest,Principal p){
		return userService.updateUser(userRequest, p);
	}


	
	@GetMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable Long id){
		return this.statusService.getUserStatus(id);
	}
	
	@MessageMapping("/status/online")
	public String updateStatusOnline(@Payload String email){
		 UserStatus status = this.statusService.updateUserStatus(email,UserStatus.ONLINE);
		this.messageTemplate.convertAndSend(AppConstants.STATUS_DESTINATION,email +" "+status.toString() );
		return  status.toString();
	}

	@MessageMapping("/status/offline")
	public String updateStatusOffline(@Payload String email){
		 UserStatus status = this.statusService.updateUserStatus(email,UserStatus.OFFILNE);
		this.messageTemplate.convertAndSend(AppConstants.STATUS_DESTINATION,email +" "+status.toString() );
		return  status.toString();
	}
	
	@MessageMapping("/search")
	public ResponseEntity<?> getUserByEmailOrName(@Payload String value){
		ResponseEntity<?> response = this.userService.getUserByEmailOrUserName(value);
		this.messageTemplate.convertAndSend("/user/open/search", response);
		return response;
	}
}
