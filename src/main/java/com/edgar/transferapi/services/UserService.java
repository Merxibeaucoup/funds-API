package com.edgar.transferapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edgar.transferapi.models.User;
import com.edgar.transferapi.repositories.UserRepository;

@Service
public class UserService {
	
	
	@Autowired
	private UserRepository userRepository;
	
	
	public User create(User user) {
		return userRepository.save(user);
	}
	
	
	// TODO needs spring security to get logged in user details
	public User getUser(User user) {
		user = new User();
		return user;
	}
	
	
	
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}

}
