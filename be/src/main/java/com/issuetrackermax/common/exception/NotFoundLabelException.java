package com.issuetrackermax.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundLabelException extends ApiException {
	public NotFoundLabelException() {
		super(HttpStatus.NOT_FOUND, "존재하지 않는 라벨입니다.");
	}
}
