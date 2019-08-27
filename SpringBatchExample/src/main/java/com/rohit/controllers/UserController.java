package com.rohit.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
		logger.trace("A TRACE Message");
		logger.debug("A DEBUG Message");
		logger.info("An INFO Message");
		logger.warn("A WARN Message");
		logger.error("An ERROR Message");
		List<User> users = userService.getAllUsers();
		return users;
	}
}
