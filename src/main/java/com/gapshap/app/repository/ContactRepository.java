package com.gapshap.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gapshap.app.model.chat.Contacts;

@Repository
public interface ContactRepository extends JpaRepository<Contacts, Long>{

	@Query("SELECT c FROM Contacts c WHERE c.owner.email=:ownerEmail AND c.contact.email=:recipientEmail")
	Optional<Contacts> findByOwnerAndContact(String ownerEmail,String recipientEmail);
	
	@Query("SELECT c FROM Contacts c WHERE c.owner.email=:email")
	List<Contacts> findByOwner(String email);
}
