package com.gapshap.app.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gapshap.app.constants.AppConstants;
import com.gapshap.app.constants.InvitationStatus;
import com.gapshap.app.exception.ResourceNotFoundException;
import com.gapshap.app.exception.UserNotFoundException;
import com.gapshap.app.model.User;
import com.gapshap.app.model.chat.InvitationRequest;
import com.gapshap.app.payload.InvitationSendRequest;
import com.gapshap.app.payload.UpdateInvitationRequest;
import com.gapshap.app.repository.InvitationRepository;
import com.gapshap.app.repository.UserRepository;
import com.gapshap.app.service.IInvitationService;

@Service
public class InvitationServiceImpl implements IInvitationService {

	@Autowired
	private InvitationRepository invitationRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseEntity<?> sendInvitation(InvitationSendRequest request) {
		Map<String, Object> response = new HashMap<>();
		Map<String, Object> isPresent = (Map<String, Object>) this.getInvitationBySenderAndRecipient(request).getBody();
		if (Objects.nonNull(isPresent.get(AppConstants.DATA_MESSAGE))) {
			response.put(AppConstants.MESSAGE, AppConstants.INVITATION_ALREADY_SENT);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		InvitationRequest invitation = new InvitationRequest();

		invitation.setCreatedAt(LocalDateTime.now());
		invitation.setRequestStatus(InvitationStatus.NEW);

		User sender = this.userRepository.findById(request.getSender())
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		invitation.setSender(sender);

		User recipient = this.userRepository.findById(request.getRecipient())
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		invitation.setRecipient(recipient);
		response.put(AppConstants.MESSAGE, AppConstants.INVITATION_SENT);
		this.invitationRepository.save(invitation);
		return null;
	}

	@Override
	public ResponseEntity<?> updateInvitaion(UpdateInvitationRequest request) {
		Map<String, Object> response = new HashMap<>();
		InvitationRequest invitation = this.invitationRepository.findById(request.getId())
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.INVITATION_NOT_FOUND));
		invitation.setRequestStatus(request.getRequestStatus());
		this.invitationRepository.save(invitation);
		response.put(AppConstants.MESSAGE, AppConstants.INVITATION_UPDATE_SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<?> getInvitationBySenderAndRecipient(InvitationSendRequest request) {

		Map<String, Object> response = new HashMap<>();
		User sender = new User();
		sender.setId(request.getSender());
		User recipient = new User();
		recipient.setId(request.getRecipient());

		Optional<InvitationRequest> present = this.invitationRepository.findBySenderAndRecipient(sender, recipient);
		if (present.isEmpty()) {
			present = this.invitationRepository.findBySenderAndRecipient(recipient, sender);
			if (present.isEmpty()) {
				response.put(AppConstants.MESSAGE, AppConstants.INVITATION_NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
		}
		response.put(AppConstants.MESSAGE, AppConstants.INVITATION_RETREIVED);
		response.put(AppConstants.DATA_MESSAGE, present.get());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

}
