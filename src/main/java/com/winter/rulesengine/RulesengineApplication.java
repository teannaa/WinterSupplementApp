package com.winter.rulesengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RulesengineApplication {

	public static void main(String[] args) {
		SpringApplication.run(RulesengineApplication.class, args);
	}

}
