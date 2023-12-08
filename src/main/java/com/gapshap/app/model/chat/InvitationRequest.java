package com.gapshap.app.model.chat;

import java.time.LocalDateTime;

import com.gapshap.app.constants.InvitationStatus;
import com.gapshap.app.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InvitationRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User sender;
	
	@ManyToOne
	@JoinColumn(name = "recipient_id")
	private User recipient;
	
	private Boolean isAccepted=false;
	
	@Enumerated(EnumType.STRING)
	private InvitationStatus requestStatus;
	
	private LocalDateTime createdAt;
	
}
