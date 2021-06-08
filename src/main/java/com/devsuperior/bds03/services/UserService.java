package com.devsuperior.bds03.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsuperior.bds03.entities.User;
import com.devsuperior.bds03.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username);
		if (user == null) {
			logger.error(String.format("User not found: %s", username));
			throw new UsernameNotFoundException(String.format("User \"%s\" not found", username));
		}
		logger.info(String.format("User found: %s", username));
		return user;
	}
}
