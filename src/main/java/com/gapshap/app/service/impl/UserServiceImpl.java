package com.gapshap.app.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gapshap.app.constants.AppConstants;
import com.gapshap.app.exception.UserNotFoundException;
import com.gapshap.app.model.User;
import com.gapshap.app.model.UserRole;
import com.gapshap.app.payload.PageReqst;
import com.gapshap.app.payload.UserRequest;
import com.gapshap.app.payload.UserResponse;
import com.gapshap.app.payload.UserStatusResponse;
import com.gapshap.app.repository.UserRepository;
import com.gapshap.app.service.IUserService;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper mapper;

	@Value("${profile.path}")
	private String DIRECTORY;

	public UserResponse userToSearchUserResponse(User user) {
		UserResponse response = new UserResponse();
		response.setId(user.getId());
		response.setProfileName(user.getProfileName());
		response.setUserName(user.getUserName());
		response.setBio(user.getBio());
		response.setId(user.getId());
		UserStatusResponse statusRes = new UserStatusResponse();
		statusRes.setLastSeen(user.getUserStatus().getLastSeen().toString());
		statusRes.setIsOnline(user.getUserStatus().getIsOnline());
		statusRes.setId(user.getUserStatus().getId());
		response.setUserStatus(statusRes);
		return response;
	}

	public UserResponse userToUserResponse(User user) {
		UserResponse response = new UserResponse();
		response.setId(user.getId());
		response.setProfileName(user.getProfileName());
		response.setUserName(user.getUserName());
		response.setBio(user.getBio());
		response.setId(user.getId());
		response.setPhoneNumber(user.getPhoneNumber());
		response.setEmail(user.getEmail());
		
		UserStatusResponse statusRes = new UserStatusResponse();
		statusRes.setLastSeen(user.getUserStatus().getLastSeen().toString());
		statusRes.setIsOnline(user.getUserStatus().getIsOnline());
		statusRes.setId(user.getUserStatus().getId());
		response.setUserStatus(statusRes);
		return response;
	}

	@Override
	public ResponseEntity<?> getAllUsers(PageReqst pageRequest) {
		Map<String, Object> response = new HashMap<>();

		Page<User> user = this.userRepository
				.findAll(PageRequest.of(pageRequest.getPageNo(), pageRequest.getPageSize()));
		response.put(AppConstants.MESSAGE, AppConstants.USER_RETIREVED_SUCCESS);
		response.put(AppConstants.DATA_MESSAGE, user.map(u -> this.userToSearchUserResponse(u)));
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<?> getUserById(Long userId) {
		Map<String, Object> response = new HashMap<>();

		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		response.put(AppConstants.MESSAGE, AppConstants.USER_RETERIEVED_SUCCESS);
		response.put(AppConstants.DATA_MESSAGE, this.userToUserResponse(user));
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<?> getUserByEmailOrUserName(String value) {
		Map<String, Object> response = new HashMap<>();
		List<User> user = this.userRepository.findByEmailOrUserNameLike(value);
		response.put(AppConstants.MESSAGE, AppConstants.USER_RETERIEVED_SUCCESS);
		response.put(AppConstants.DATA_MESSAGE, user.stream().map(u -> this.userToUserResponse(u)).collect(Collectors.toList()));
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<?> updateUser(UserRequest request, Principal p) {
		Map<String, Object> response = new HashMap<>();
		User user = this.userRepository.findByEmail(p.getName())
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		user.setBio(request.getBio());
		user.setEmail(request.getEmail());
		user.setPhoneNumber(request.getPhoneNumber());
		user.setUserName(request.getUserName());
		if (Objects.nonNull(request.getProfileName())) {

			user.setProfileName(UUID.randomUUID().toString() + request.getProfileName().getOriginalFilename());

			String fileName = StringUtils.cleanPath(user.getProfileName());
			Path path = Paths.get(System.getProperty("user.dir"), fileName);
			try {
				Files.copy(request.getProfileName().getInputStream(), path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.userRepository.save(user);
 response.put(AppConstants.MESSAGE, AppConstants.USER_UPDATED_SUCCESS);
 response.put(AppConstants.DATA_MESSAGE, this.userToSearchUserResponse(user));
		return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = this.userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		Set<UserRole> userRoles = user.getUserRoles();
		List<SimpleGrantedAuthority> authorities = userRoles.stream()
				.map(ur -> new SimpleGrantedAuthority(ur.getRoles().getTitle())).collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(email, user.getPassword(), authorities);
	}
}
