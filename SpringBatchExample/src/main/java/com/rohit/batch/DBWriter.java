package com.rohit.batch;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rohit.dao.UserRepository;
import com.rohit.model.User;

@Component
public class DBWriter implements ItemWriter<User> {

	private static final Logger logger = LoggerFactory.getLogger(DBWriter.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void write(List<? extends User> users) throws Exception {
		logger.info("Data Saved for Users:"+users);
		userRepository.save(users);		
	}

}
