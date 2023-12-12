package com.gapshap.app.service.impl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gapshap.app.constants.AppConstants;
import com.gapshap.app.exception.UserNotFoundException;
import com.gapshap.app.model.User;
import com.gapshap.app.model.chat.Conversation;
import com.gapshap.app.model.chat.Message;
import com.gapshap.app.payload.ConversationRequest;
import com.gapshap.app.payload.MessageRequest;
import com.gapshap.app.repository.ConversationRepository;
import com.gapshap.app.repository.MessageRepository;
import com.gapshap.app.repository.UserRepository;
import com.gapshap.app.service.IConversationService;
import com.gapshap.app.service.IMessageService;

import io.micrometer.observation.transport.SenderContext;

@Service
public class MessageServiceImpl implements IMessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private IConversationService conversationService;
	
	@Autowired
	private ConversationRepository conversationRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<?> saveMessage(MessageRequest messageRequest) {
		Map<String , Object> response = new HashMap<>();
		Message message  = new Message();
		message.setSentAt(LocalDateTime.now());
		message.setMessage(messageRequest.getMessage());
		message.setSender(this.userRepository.findById(messageRequest.getSender()).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND)));
		message.setRecipient(this.userRepository.findById(messageRequest.getRecipient()).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND)));
		
		response.put(AppConstants.MESSAGE, AppConstants.MESSAGE_DELIVERED);
		response.put(AppConstants.DATA_MESSAGE, message);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateMessage() {
		
		return null;
	}

	@Override
	public ResponseEntity<?> getAllMessagesBySenderAndRecipient(ConversationRequest request){
		User sender = this.userRepository.findById(request.getSender()).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		Map<String , Object> response = new HashMap<>();
             List<Message> message = this.messageRepository.findBySenderAndRecipient(sender.getId(),request.getRecipient());
             response.put(AppConstants.MESSAGE, AppConstants.MESSAGE_RETERIVED);
             response.put(AppConstants.DATA_MESSAGE, message);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
}
