package com.issuetrackermax.common.exception.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
	private boolean success;
	private final ErrorCode errorCode;

	@Builder
	public ErrorResponse(int status, String message) {
		this.success = false;
		this.errorCode = new ErrorCode(status, message);
	}
}
