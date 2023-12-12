package com.gapshap.app.service;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import com.gapshap.app.payload.ConversationRequest;
import com.gapshap.app.payload.MessageRequest;

public interface IMessageService {

	ResponseEntity<?> saveMessage(MessageRequest messageRequest);
	
	ResponseEntity<?> updateMessage();

	ResponseEntity<?> getAllMessagesBySenderAndRecipient(ConversationRequest request);
}
