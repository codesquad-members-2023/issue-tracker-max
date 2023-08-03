package com.issuetrackermax.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundIssueException extends ApiException {
	public NotFoundIssueException() {
		super(HttpStatus.NOT_FOUND, "존재하지 않는 이슈입니다.");
	}
}
