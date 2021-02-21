package com.marlabs.question.controller;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.marlabs.question.exception.QuestionServiceGeneralException;
import com.marlabs.question.model.Answer;
import com.marlabs.question.model.Question;
import com.marlabs.question.service.QuestionService;

@RunWith(MockitoJUnitRunner.class)
public class QuestionControllerTests {
	
	private static final String QUESTION = "Please sum the numbers 1,8,7";
	
	private static final String SECRET = "UGxlYXNlIHN1bSB0aGUgbnVtYmVycyAxLDgsNw==";
	
	@InjectMocks
	private QuestionService questionService;
	
	@Mock
	private Random random;
		
	@Test
	public void getQuestionTest() {
		Question question = questionService.getQuestion();
		Assert.assertNotNull(question);
	}

	@Test
	public void validateQuestionTest() {
		Answer answer = new Answer(QUESTION, SECRET, 16);
		String result = questionService.validateQuestion(answer);
		Assert.assertNotNull(result);
	}
	
	@Test(expected = QuestionServiceGeneralException.class)
	public void validateQuestionExceptionTest() {
		Answer answer = new Answer(QUESTION, SECRET, 14);
		questionService.validateQuestion(answer);
	}
	
	@Test(expected = QuestionServiceGeneralException.class)
	public void validateQuestionWithGeneralExceptionTest() {
		Answer answer = new Answer("", "", 0);
		questionService.validateQuestion(answer);
	}
}

