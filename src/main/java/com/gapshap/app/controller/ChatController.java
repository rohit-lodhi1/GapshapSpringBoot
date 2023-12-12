package com.gapshap.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gapshap.app.model.chat.Message;
import com.gapshap.app.payload.MessageRequest;
import com.gapshap.app.service.IMessageService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/gapshap/chat")
public class ChatController {

	@Autowired
	private IMessageService messageService;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@MessageMapping("/chat")
	private ResponseEntity<?> sendMessage(@Payload MessageRequest messageRequest) {
		this.messagingTemplate.convertAndSend("/user/"+messageRequest.getConversationId(),messageRequest);
		return this.messageService.saveMessage(messageRequest);
	}
	
	
}
