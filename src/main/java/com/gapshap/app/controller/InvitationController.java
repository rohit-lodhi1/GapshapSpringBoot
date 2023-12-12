package com.gapshap.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gapshap.app.payload.InvitationSendRequest;
import com.gapshap.app.service.IInvitationService;

@RestController
@RequestMapping("/gapshap/invitation")
@CrossOrigin("*")
public class InvitationController {

	@Autowired
	private SimpMessagingTemplate messageTemplate;
	
	@Autowired
	private IInvitationService invitationService;
	
	@MessageMapping("/invite")
	public ResponseEntity<?> sendInvite(@Payload InvitationSendRequest request,Principal p){
		 
		this.messageTemplate.convertAndSend("/user/"+request.getRecipient(),request);
		return  this.invitationService.sendInvitation(request);
		
	}
	
	@MessageMapping("/invite/delete")
	public ResponseEntity<?> deleteInvite(@Payload Long id,Principal p){
		ResponseEntity<?> response = this.invitationService.deleteInvitaion(id);
		this.messageTemplate.convertAndSend("/user/"+p.getName(),response);
		return response;
	}
}
