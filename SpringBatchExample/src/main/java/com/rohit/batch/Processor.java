package com.rohit.batch;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.rohit.model.User;

@Component
public class Processor implements ItemProcessor<User,User>{

	private static final Logger logger = LoggerFactory.getLogger(Processor.class);
	private static final Map<String,String> PROFESSSION_NAMES= new HashMap<>();
	
	
	
	public Processor() {
		PROFESSSION_NAMES.put("001", "IT");
		PROFESSSION_NAMES.put("002", "HR");
		PROFESSSION_NAMES.put("003", "ADMIN");
		
	}



	@Override
	public User process(User user) throws Exception {
		String deptCode = user.getProfession();
		String profession = PROFESSSION_NAMES.get(deptCode);
		user.setProfession(profession);
		logger.info(String.format("Converted from [%s] to [%s]", deptCode,profession));
		return user;
	}

}
