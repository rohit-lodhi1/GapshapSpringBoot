package com.gapshap.app.model.chat;

import java.time.LocalDateTime;

import com.gapshap.app.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String message;
	
	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User sender;
	
	@ManyToOne
	@JoinColumn(name="recipient_id")
	private User recipient;

	private LocalDateTime sentAt;
	
	private Boolean isSeen=false;

	private Boolean isDelivered=false;
	
	private LocalDateTime seenAt;
	
	private LocalDateTime deliveredAt;
	
	private String conversation_id;
}
