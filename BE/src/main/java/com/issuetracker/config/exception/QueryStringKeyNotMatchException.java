package com.issuetracker.config.exception;

import org.springframework.http.HttpStatus;

public class QueryStringKeyNotMatchException extends ApiException {

	private static final String MESSAGE = "올바른 QueryString Key가 아닙니다.";

	public QueryStringKeyNotMatchException() {
		super(HttpStatus.BAD_REQUEST, MESSAGE);
	}
}
