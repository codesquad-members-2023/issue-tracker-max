package kr.codesquad.issuetracker.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private final ErrorCode errorCode;
	private final String message;

	public ErrorResponse(ErrorCode errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public ErrorResponse(ErrorCode errorCode) {
		this(errorCode, errorCode.getMessage());
	}
}
