package com.rohit.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohit.dao.UserRepository;
import com.rohit.model.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void initDB()
	{
		List<User> users = new ArrayList<>();
		users.add(new User(1, "R", "IT", 32) );
		users.add(new User(2, "A", "HR", 32) );
		users.add(new User(3, "D", "HR", 34) );
		users.add(new User(4, "X", "IT", 35) );
		userRepository.save(users);
		
	}
	
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}
	
	public void saveUser(User user)
	{
		userRepository.save(user);
	}

}
