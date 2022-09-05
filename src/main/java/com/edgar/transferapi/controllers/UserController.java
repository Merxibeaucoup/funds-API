package com.edgar.transferapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edgar.transferapi.models.User;
import com.edgar.transferapi.repositories.UserRepository;
import com.edgar.transferapi.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	
	// declare user Service && user Repository
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	
	@PostMapping
	public ResponseEntity<User> registerUser(@RequestBody User user){
		User newUser = userService.create(user);
		return ResponseEntity.ok(newUser);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable long id){
		return userRepository.findById(id)
		           .map(record -> {
		               userRepository.deleteById(id);
		               return ResponseEntity.ok().build();
		           }).orElse(ResponseEntity.notFound().build());
	}

}
