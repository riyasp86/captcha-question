package com.marlabs.question.model;

import javax.validation.constraints.NotBlank;

public class Question {

	@NotBlank(message = "Question cannot be missing or empty")	
	private String question;
	
	@NotBlank(message = "Secret cannot be missing or empty")
	private String secret;

	public Question() {
		
	}
	
	public Question(String question, String secret) {
		this.question = question;
		this.secret = secret;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}