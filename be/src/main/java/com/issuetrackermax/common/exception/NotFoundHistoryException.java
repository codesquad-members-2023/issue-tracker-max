package com.issuetrackermax.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundHistoryException extends ApiException {
	public NotFoundHistoryException() {
		super(HttpStatus.NOT_FOUND, "존재하지 않는 히스토리입니다.");
	}
}
