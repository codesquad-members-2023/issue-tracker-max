package com.issuetrackermax.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundAssigneeException extends ApiException {
	public NotFoundAssigneeException() {
		super(HttpStatus.NOT_FOUND, "존재하지 않는 담당자입니다.");
	}
}
