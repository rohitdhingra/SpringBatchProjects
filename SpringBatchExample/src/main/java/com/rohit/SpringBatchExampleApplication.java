package com.rohit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class SpringBatchExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchExampleApplication.class, args);
	}

}
