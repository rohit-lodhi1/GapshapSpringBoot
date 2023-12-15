package com.gapshap.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gapshap.app.constants.InvitationStatus;
import com.gapshap.app.model.User;
import com.gapshap.app.model.chat.InvitationRequest;

@Repository
public interface InvitationRepository extends JpaRepository<InvitationRequest, Long>{

	
	Optional<InvitationRequest> findBySenderAndRecipient(User sender,User recipient);
	
	Page<InvitationRequest> findByRecipientAndRequestStatusOrderByCreatedAtDesc(User recipient,InvitationStatus status,Pageable page);
}
