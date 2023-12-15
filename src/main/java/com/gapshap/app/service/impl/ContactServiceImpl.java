package com.gapshap.app.service.impl;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gapshap.app.constants.AppConstants;
import com.gapshap.app.exception.UserNotFoundException;
import com.gapshap.app.model.User;
import com.gapshap.app.model.chat.Contacts;
import com.gapshap.app.payload.ContactRequest;
import com.gapshap.app.repository.ContactRepository;
import com.gapshap.app.repository.UserRepository;
import com.gapshap.app.service.IContactService;

@Service
public class ContactServiceImpl implements IContactService{

	@Autowired
	private ContactRepository contactRepository;
	

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ResponseEntity<?> createContact(ContactRequest request) {
		Map<String , Object> response = new HashMap<>();
		User owner = this.userRepository.findByEmail(request.getOwner()).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		User recipient = this.userRepository.findByEmail(request.getContact()).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		Contacts c1 = new Contacts();
		c1.setContact(recipient);
		c1.setOwner(owner);
		c1.setCreatedAt(LocalDateTime.now());
		
		Contacts c2 = new Contacts();
		c2.setContact(owner);
		c2.setOwner(recipient);
		c2.setCreatedAt(LocalDateTime.now());
		
		this.contactRepository.save(c1);
		this.contactRepository.save(c2);
		response.put(AppConstants.MESSAGE, AppConstants.CONTACT_RETRIEVED);
		response.put(AppConstants.DATA_MESSAGE, c1);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getContactOfOwner(String ownerEmail) {
		Map<String , Object> response = new HashMap<>();
          List<Contacts> contacts = this.contactRepository.findByOwner(ownerEmail);
          response.put(AppConstants.MESSAGE, AppConstants.CONTACT_RETRIEVED);
          response.put(AppConstants.DATA_MESSAGE, contacts);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteContact(Long id) {
		
		return null;
	}

}
