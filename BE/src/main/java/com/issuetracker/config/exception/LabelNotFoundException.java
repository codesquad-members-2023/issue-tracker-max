package com.issuetracker.config.exception;

import org.springframework.http.HttpStatus;

public class LabelNotFoundException extends ApiException {

	private static final String MESSAGE = "레이블이 존재하지 않습니다.";

	public LabelNotFoundException() {
		super(HttpStatus.NOT_FOUND, MESSAGE);
	}
}
