package com.gapshap.app.service.impl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gapshap.app.constants.AppConstants;
import com.gapshap.app.constants.InvitationStatus;
import com.gapshap.app.exception.ResourceNotFoundException;
import com.gapshap.app.exception.UserNotFoundException;
import com.gapshap.app.model.User;
import com.gapshap.app.model.chat.Contacts;
import com.gapshap.app.model.chat.InvitationRequest;
import com.gapshap.app.payload.InvitationResponse;
import com.gapshap.app.payload.InvitationSendRequest;
import com.gapshap.app.payload.UpdateInvitationRequest;
import com.gapshap.app.repository.ContactRepository;
import com.gapshap.app.repository.InvitationRepository;
import com.gapshap.app.repository.UserRepository;
import com.gapshap.app.service.IInvitationService;

@Service
public class InvitationServiceImpl implements IInvitationService {

	@Autowired
	private InvitationRepository invitationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public ResponseEntity<?> sendInvitation(InvitationSendRequest request) {
		Map<String, Object> response = new HashMap<>();

		InvitationRequest invitation = new InvitationRequest();

		invitation.setCreatedAt(LocalDateTime.now());
		invitation.setRequestStatus(InvitationStatus.NEW);

		User sender = this.userRepository.findByEmail(request.getSender())
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		invitation.setSender(sender);

		User recipient = this.userRepository.findByEmail(request.getRecipient())
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

		// adding user to contact list if invitation accepted
		if (request.getRequestStatus().equals(InvitationStatus.ACCEPTED)) {
			User sender = this.userRepository.findById(invitation.getSender().getId())
					.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
			User receiver = this.userRepository.findById(invitation.getRecipient().getId())
					.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));

			Contacts contact = new Contacts();
			contact.setContact(receiver);
			contact.setOwner(sender);
			contact.setCreatedAt(LocalDateTime.now());

			Contacts contact2 = new Contacts();
			contact.setContact(sender);
			contact.setOwner(receiver);
			contact2.setCreatedAt(LocalDateTime.now());

			this.contactRepository.save(contact);
			this.contactRepository.save(contact2);
		}

		this.invitationRepository.save(invitation);
		response.put(AppConstants.MESSAGE, AppConstants.INVITATION_UPDATE_SUCCESS);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	public ResponseEntity<?> getAllNotificationsOfUser(Principal p, Integer pageNo, Integer pageSize) {

		User user = this.userRepository.findByEmail(p.getName())
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));

		Page<InvitationRequest> invitations = this.invitationRepository
				.findByRecipientAndRequestStatusOrderByCreatedAtDesc(user, InvitationStatus.NEW,
						PageRequest.of(pageNo, pageSize));

		Map<String, Object> response = new HashMap<>();

		response.put(AppConstants.MESSAGE, AppConstants.INVITATION_RETREIVED);
		response.put(AppConstants.DATA_MESSAGE, invitations.map(i -> this.mapper.map(i, InvitationResponse.class)));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getInvitationBySenderAndRecipient(InvitationSendRequest request) {

		Map<String, Object> response = new HashMap<>();
		User sender = this.userRepository.findByEmail(request.getSender())
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
	

		User recipient = this.userRepository.findByEmail(request.getRecipient())
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));

		Optional<InvitationRequest> present = this.invitationRepository.findBySenderAndRecipient(sender, recipient);
		if (present.isEmpty()) {

			Optional<InvitationRequest> isHeSent = this.invitationRepository.findBySenderAndRecipient(recipient,
					sender);
			if (isHeSent.isEmpty()) {
				response.put(AppConstants.MESSAGE, AppConstants.INVITATION_NOT_FOUND);
				
				
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.put("sentBy", AppConstants.RECIPIENT);
			response.put(AppConstants.MESSAGE, AppConstants.INVITATION_RETREIVED);
			response.put(AppConstants.DATA_MESSAGE, present.get());
			return new ResponseEntity<>(response, HttpStatus.OK);

		}
		response.put("sentBy", AppConstants.SENDER);
		response.put(AppConstants.MESSAGE, AppConstants.INVITATION_RETREIVED);
		response.put(AppConstants.DATA_MESSAGE, present.get());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> deleteInvitaion(Long id){
		Map<String, Object> response = new HashMap<>();
		this.invitationRepository.deleteById(id);
		response.put(AppConstants.MESSAGE,AppConstants.INVITAION_DELETED );
		return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
	}

}
