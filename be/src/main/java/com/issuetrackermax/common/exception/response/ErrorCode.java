package com.issuetrackermax.common.exception.response;

import lombok.Getter;

@Getter
public class ErrorCode {
	private final int status;
	private final String message;

	public ErrorCode(int status, String message) {
		this.status = status;
		this.message = message;
	}
}
