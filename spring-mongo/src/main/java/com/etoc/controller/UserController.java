package com.etoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.etoc.model.User;
import com.etoc.repository.UserRepository;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/user/{userName}/")
	public ResponseEntity<?> get(@PathVariable("userName") String userName) {
		User user = userRepository.findUserByUserName(userName);
		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}

	
	@PostMapping(value = "/", produces = { "application/json;charset=UTF-8" })
	public ResponseEntity<?> add(@RequestBody User sysUsers) {
		userRepository.saveUser(sysUsers);
		return new ResponseEntity<>( HttpStatus.OK);
	}
}
