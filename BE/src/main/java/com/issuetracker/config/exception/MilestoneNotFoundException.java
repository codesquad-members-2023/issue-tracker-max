package com.issuetracker.config.exception;

import org.springframework.http.HttpStatus;

public class MilestoneNotFoundException extends ApiException {

	private static final String MESSAGE = "해당 마일스톤이 존재하지 않습니다.";

	public MilestoneNotFoundException() {
		super(HttpStatus.NOT_FOUND, MESSAGE);
	}
}
