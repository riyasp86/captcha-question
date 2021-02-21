package com.marlabs.question.configuration;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuestionConfiguration {

	@Bean
	public Random random() {
		return new Random();
	}
}
