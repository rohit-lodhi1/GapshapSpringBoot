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
import com.gapshap.app.model.chat.Contacts;
import com.gapshap.app.model.chat.Conversation;
import com.gapshap.app.model.chat.Message;
import com.gapshap.app.payload.ConversationRequest;
import com.gapshap.app.payload.MessageRequest;
import com.gapshap.app.repository.ContactRepository;
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
	
	@Autowired
	private ContactRepository contactRepository;

	@Override
	public ResponseEntity<?> saveMessage(MessageRequest messageRequest) {
		
		Optional<Contacts> contact = this.contactRepository.findByOwnerAndContact(messageRequest.getSender(), messageRequest.getRecipient());
		User sender = this.userRepository.findByEmail(messageRequest.getSender()).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		User recipient = this.userRepository.findByEmail(messageRequest.getRecipient()).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		if(contact.isEmpty()) {
			Contacts c1 = new Contacts();
			c1.setContact(recipient);
			c1.setOwner(sender);
			c1.setCreatedAt(LocalDateTime.now());
			
			Contacts c2 = new Contacts();
			c2.setContact(sender);
			c2.setOwner(recipient);
			c2.setCreatedAt(LocalDateTime.now());
			
			this.contactRepository.save(c1);
			this.contactRepository.save(c2);
			
		}
		
		
		Map<String , Object> response = new HashMap<>();
		Message message  = new Message();
		message.setSentAt(LocalDateTime.now());
		message.setMessage(messageRequest.getMessage());
		message.setSender(sender);
		message.setRecipient(recipient);
		
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
		User sender = this.userRepository.findByEmail(request.getSender()).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		Map<String , Object> response = new HashMap<>();
             List<Message> message = this.messageRepository.findBySenderAndRecipient(sender.getEmail(),request.getRecipient());
             response.put(AppConstants.MESSAGE, AppConstants.MESSAGE_RETERIVED);
             response.put(AppConstants.DATA_MESSAGE, message);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
}
