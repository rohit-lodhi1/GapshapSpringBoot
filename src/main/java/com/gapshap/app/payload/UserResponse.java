package com.gapshap.app.payload;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.gapshap.app.model.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class UserResponse {

private String userName;
	
	private String email;
	
	private String password;
	
	private String phoneNumber;
	
	private String profileName;
	
	private Boolean isActive;
	
	private LocalDateTime createdAt;
	
	 private LocalDateTime updateAt;
	 
	 private Set<UserRole> userRoles;
}
