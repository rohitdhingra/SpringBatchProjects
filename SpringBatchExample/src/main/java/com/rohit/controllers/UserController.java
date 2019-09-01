package com.rohit.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.model.User;
import com.rohit.service.UserService;

@RestController
@RequestMapping("/cp")
public class UserController {

	final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/getUsers")
	public List<User> getAllUsers() {
		logger.info("Get All Users");
		List<User> users = userService.getAllUsers();
		return users;
	}
	
	@PostMapping(value="/saveUser",consumes="application/json")
	public String saveUser(@RequestBody User user) {
		logger.info("Save User");
		userService.saveUser(user);
		return "User saved successfully with user Id"+user.getId();
	}
}
