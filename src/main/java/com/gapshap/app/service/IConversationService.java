package com.gapshap.app.service;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.gapshap.app.payload.ConversationRequest;


public interface IConversationService {

	ResponseEntity<?> createConversation(ConversationRequest request, Principal p);
	
	ResponseEntity<?> getConversation(ConversationRequest request, Principal p);

	ResponseEntity<?> deleteConversation(ConversationRequest request, Principal p);

    
	
}
