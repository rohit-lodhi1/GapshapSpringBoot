package com.gapshap.app.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gapshap.app.constants.AppConstants;
import com.gapshap.app.exception.UserNotFoundException;
import com.gapshap.app.model.User;
import com.gapshap.app.model.UserRole;
import com.gapshap.app.repository.UserRepository;
import com.gapshap.app.service.IUserService;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND));
		Set<UserRole> userRoles = user.getUserRoles();
		List<SimpleGrantedAuthority> authorities = userRoles.stream().map(ur -> new SimpleGrantedAuthority(ur.getRoles().getTitle())).collect(Collectors.toList());
		
		return new org.springframework.security.core.userdetails.User(email, user.getPassword(), authorities);
	}

}
