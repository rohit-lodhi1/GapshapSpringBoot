package com.gapshap.app.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.gapshap.app.model.chat.Contacts;
import com.gapshap.app.model.chat.UserActiveStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String userName;
	
	private String email;
	
	private String password;
	
	private String phoneNumber;
	
	private String bio;
	
	private String profileName;
	
	private Boolean isActive=true;
	
	private LocalDateTime createdAt;
	
	 private LocalDateTime updateAt;
	 
	 @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	 @JoinColumn(name="u_id")
	 private Set<UserRole> userRoles;
	 
	 @OneToOne(cascade = CascadeType.ALL)
	 private UserActiveStatus  userStatus;
	 
	 @OneToMany
	 @JoinColumn(name="owner_id")
	 public List<Contacts> contacts;
}
