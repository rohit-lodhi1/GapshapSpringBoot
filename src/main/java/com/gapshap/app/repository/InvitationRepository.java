package com.gapshap.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gapshap.app.model.User;
import com.gapshap.app.model.chat.InvitationRequest;

@Repository
public interface InvitationRepository extends JpaRepository<InvitationRequest, Long>{

	Optional<InvitationRequest> findBySenderAndRecipient(User sender,User recipient);
}
