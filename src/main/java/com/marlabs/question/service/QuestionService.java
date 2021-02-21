package com.marlabs.question.service;

import static com.marlabs.question.util.QuestionConstants.ERROR_MESSAGE;
import static com.marlabs.question.util.QuestionConstants.FAILURE_MESSAGE;
import static com.marlabs.question.util.QuestionConstants.QUESTION_PREFIX;
import static com.marlabs.question.util.QuestionConstants.SUCCESS_MESSAGE;

import java.util.Base64;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.marlabs.question.exception.QuestionServiceGeneralException;
import com.marlabs.question.model.Answer;
import com.marlabs.question.model.Question;

@Service
public class QuestionService {
	
	@Autowired
	private Random random;
	
	public Question getQuestion() {
		IntStream intNumbers = random.ints(3, 1, 10);
		String question = QUESTION_PREFIX + intNumbers.boxed().map(String::valueOf).collect(Collectors.joining(","));
		String secret = Base64.getEncoder().encodeToString(question.getBytes());
		return new Question(question, secret);
	}

	public String validateQuestion(Answer answer) {
		String question = answer.getQuestion();
		String secret = answer.getSecret();
		try {
			if(Base64.getEncoder().encodeToString(question.getBytes()).equals(secret)) {
				int sum = Stream.of(answer.getQuestion().substring(QUESTION_PREFIX.length())
						.split(",")).map(Integer::parseInt).reduce(0, Integer::sum);
				if(sum != answer.getSum()) {
					throw new QuestionServiceGeneralException(HttpStatus.BAD_REQUEST, FAILURE_MESSAGE);
				}
			}			
			else {
				throw new QuestionServiceGeneralException(HttpStatus.BAD_REQUEST, FAILURE_MESSAGE);
			}
		}
		catch (QuestionServiceGeneralException captchaServiceGeneralException) {
			throw new QuestionServiceGeneralException(captchaServiceGeneralException.getHttpStatus(), captchaServiceGeneralException.getMessage());
		}
		catch (Exception e) {
			throw new QuestionServiceGeneralException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MESSAGE);
		}
		return SUCCESS_MESSAGE;
	}
}