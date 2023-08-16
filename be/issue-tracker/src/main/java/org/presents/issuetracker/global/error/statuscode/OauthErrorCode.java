package org.presents.issuetracker.global.error.statuscode;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OauthErrorCode implements StatusCode {
	// 500
	LOGIN_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "github 로그인 실패");

	private final HttpStatus httpStatus;
	private final String message;

	@Override
	public String getName() {
		return name();
	}

	@Override
	public HttpStatus getHttpStatus() {
		return this.getHttpStatus();
	}

	@Override
	public String getMessage() {
		return this.getMessage();
	}
}
