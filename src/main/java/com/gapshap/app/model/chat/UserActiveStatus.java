package com.gapshap.app.model.chat;

import java.time.LocalDateTime;

import com.gapshap.app.constants.UserStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserActiveStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDateTime lastSeen;
	
	private Boolean isOnline;
	
	@Enumerated(EnumType.STRING)
	private UserStatus status=UserStatus.OFFLINE;
	
}
