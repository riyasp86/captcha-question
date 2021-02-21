package com.marlabs.question.model;

public class Answer extends Question {
	
	private int sum;
	
	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public Answer() {
	
	}
	
	public Answer(String question, String secret, int sum) {
		super(question, secret);
		this.sum = sum;
	}
}
