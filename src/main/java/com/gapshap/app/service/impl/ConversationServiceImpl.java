package com.gapshap.app.service.impl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gapshap.app.constants.AppConstants;
import com.gapshap.app.exception.ResourceNotFoundException;
import com.gapshap.app.exception.UserNotFoundException;
import com.gapshap.app.model.User;
import com.gapshap.app.model.chat.Conversation;
import com.gapshap.app.payload.ConversationRequest;
import com.gapshap.app.repository.ConversationRepository;
import com.gapshap.app.repository.UserRepository;
import com.gapshap.app.service.IConversationService;

@Service
public class ConversationServiceImpl implements IConversationService {

	@Autowired
	private ConversationRepository conversationRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<?> createConversation(ConversationRequest request,Principal p) {
		Map<String, Object> response = new HashMap<>();
          
		User sender = this.userRepository.findByEmail(p.getName()).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		User recipient = userRepository.findById(request.getRecipient())
		.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		
		Optional<Conversation> conversation = this.conversationRepository
				.findBySenderAndRecipient(sender.getId(), recipient.getId());
		if (conversation.isPresent()) {
			response.put(AppConstants.MESSAGE, AppConstants.CONVERSATION_RETREIVED);
			response.put(AppConstants.DATA_MESSAGE, conversation.get());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		Conversation conver = new Conversation();
		String random = UUID.randomUUID().toString();
		conver.setConversationId(random);
		conver.setSender(sender);
		conver.setRecipient(recipient);
		conver.setCreatedAt(LocalDateTime.now());

		Conversation conver1 = new Conversation();
		conver1.setConversationId(random);
		conver1.setSender(recipient);
		conver1.setRecipient(sender);
		conver1.setCreatedAt(LocalDateTime.now());
		this.conversationRepository.save(conver);
		this.conversationRepository.save(conver1);
		response.put(AppConstants.MESSAGE, AppConstants.CONVERSATION_RETREIVED);
		response.put(AppConstants.DATA_MESSAGE, conver);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> getConversation(ConversationRequest request,Principal p) {
		Map<String, Object> response = new HashMap<>();
		User sender = this.userRepository.findByEmail(p.getName()).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		Optional<Conversation> conversation = this.conversationRepository
				.findBySenderAndRecipient(sender.getId(), request.getRecipient());
        
		if(conversation.isPresent()) {
		
		response.put(AppConstants.MESSAGE, AppConstants.CONVERSATION_RETREIVED);
		response.put(AppConstants.DATA_MESSAGE, conversation);
		return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.put(AppConstants.MESSAGE, AppConstants.CONVERSATION_NOT_FOUND);
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);

	}

	@Override
	public ResponseEntity<?> deleteConversation(ConversationRequest request,Principal p) {
		User sender = this.userRepository.findByEmail(p.getName()).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		Conversation conversation = this.conversationRepository.findBySenderAndRecipient(sender.getId(), request.getRecipient()).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CONVERSATION_NOT_FOUND));
		this.conversationRepository.delete(conversation);
		Map<String, Object> response = new HashMap<>();
		response.put(AppConstants.MESSAGE, AppConstants.CONVERSATION_DELETED_SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
