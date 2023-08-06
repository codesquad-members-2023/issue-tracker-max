package com.issuetracker.config.exception;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends ApiException {

	private static final String MESSAGE = "해당 멤버가 존재하지 않습니다.";

	public MemberNotFoundException() {
		super(HttpStatus.NOT_FOUND, MESSAGE);
	}
}
