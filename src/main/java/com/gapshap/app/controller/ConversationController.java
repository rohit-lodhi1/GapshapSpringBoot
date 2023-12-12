package com.gapshap.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gapshap.app.payload.ConversationRequest;
import com.gapshap.app.service.IConversationService;

@RestController
@RequestMapping("/gapshap/conversation")
@CrossOrigin("*")
public class ConversationController {

	@Autowired
	private IConversationService conversationService;
	
	
	@PostMapping("/")
	public ResponseEntity<?> createConversation(@RequestBody ConversationRequest request,Principal p){
		return this.conversationService.createConversation(request,p);
	}
	
	@PostMapping("/get")
	public ResponseEntity<?> getConversationOfRecipient(@RequestBody ConversationRequest request,Principal p){
		return this.conversationService.getConversation(request, p);
	}
	
}
