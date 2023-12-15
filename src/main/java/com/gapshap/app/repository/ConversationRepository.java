package com.gapshap.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gapshap.app.model.chat.Conversation;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long>{

	@Query("SELECT c FROM Conversation c WHERE  c.sender.email=:sender AND c.recipient.email=:recipient")
	Optional<Conversation> findBySenderAndRecipient(String sender,String recipient);
}
