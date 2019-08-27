package com.rohit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.model.User;

public interface UserRepository extends JpaRepository<User,Integer>{

}
