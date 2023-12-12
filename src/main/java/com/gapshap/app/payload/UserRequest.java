package com.gapshap.app.payload;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

	private String userName;

	private String email;

	private String password;

	private String phoneNumber;

	private String bio;

	private MultipartFile profileName;
}
