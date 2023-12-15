package com.gapshap.app.service;

import org.springframework.http.ResponseEntity;

import com.gapshap.app.payload.ContactRequest;

public interface IContactService {
   ResponseEntity<?> createContact(ContactRequest request);
   
   ResponseEntity<?> getContactOfOwner(String ownerId);
   
   ResponseEntity<?> deleteContact(Long id);
}
