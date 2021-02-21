package com.marlabs.question.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class QuestionServiceExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, 
			HttpHeaders headers, 
			HttpStatus status, 
			WebRequest request) {
		List<String> errors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		QuestionServiceException questionServiceError = 
				new QuestionServiceException(HttpStatus.BAD_REQUEST, errors);
			return handleExceptionInternal(ex, questionServiceError, headers, questionServiceError.getStatus(), request);
	}
	
	@ExceptionHandler({ QuestionServiceGeneralException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public QuestionServiceException handleURWAPIException(
			QuestionServiceGeneralException questionServiceGeneralException) {
		return new QuestionServiceException(questionServiceGeneralException.getHttpStatus(),
				questionServiceGeneralException.getMessage());
	}
}
