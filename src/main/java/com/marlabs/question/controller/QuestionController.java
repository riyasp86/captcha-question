package com.marlabs.question.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marlabs.question.model.Answer;
import com.marlabs.question.model.Question;
import com.marlabs.question.service.QuestionService;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@GetMapping
	public ResponseEntity<Question> getQuestion() {
		return new ResponseEntity<>(questionService.getQuestion(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> validateQuestion(@Valid @RequestBody Answer answer) {
		return new ResponseEntity<>(questionService.validateQuestion(answer), HttpStatus.OK);
	}
	
	
}