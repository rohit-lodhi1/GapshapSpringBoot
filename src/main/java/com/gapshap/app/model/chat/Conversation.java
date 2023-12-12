package com.gapshap.app.model.chat;

import java.time.LocalDateTime;

import com.gapshap.app.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Conversation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String conversationId;
	
	@ManyToOne
	private User sender;
	
	@ManyToOne
	private User recipient;
	
	private LocalDateTime createdAt;
}
