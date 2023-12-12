package com.gapshap.app.payload;

import java.time.LocalDateTime;

import com.gapshap.app.model.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

private String message;
	
	
	private Long sender;

	private Long recipient;
	
	private String conversationId;

}
