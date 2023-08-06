package com.issuetracker.config;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {

	private HttpStatus status;
	private String message;

	public Integer getStatusCode() {
		return status.value();
	}

	public String getStatus() {
		return status.getReasonPhrase();
	}

	public String getMessage() {
		return message;
	}
}
