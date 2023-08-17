package com.issuetracker.common.config;

import com.issuetracker.common.config.exception.ErrorType;

public class ErrorResponse {

	private String message;

	public ErrorResponse(String message) {
		this.message = message;
	}

	public ErrorResponse(ErrorType errorType) {
		this.message = errorType.getMessage();
	}

	public String getMessage() {
		return message;
	}
}
