package com.marlabs.question.exception;

import org.springframework.http.HttpStatus;

public class QuestionServiceGeneralException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7916515224339585186L;

	private HttpStatus httpStatus;

	public QuestionServiceGeneralException(HttpStatus httpStatus, String message) {
			super(message);
			this.httpStatus = httpStatus;
		}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}
