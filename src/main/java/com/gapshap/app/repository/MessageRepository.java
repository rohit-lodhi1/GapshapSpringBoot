package com.gapshap.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gapshap.app.model.chat.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

	@Query("SELECT m FROM Message m WHERE m.sender.email=:sender AND m.recipient.email=:recipient ORDER BY m.sentAt DESC")
	List<Message> findBySenderAndRecipient(String sender,String recipient);
}
