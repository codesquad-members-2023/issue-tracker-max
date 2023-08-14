package org.presents.issuetracker.global.error.statuscode;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorCode implements StatusCode {
	DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, "중복된 아이디가 존재합니다."),
	NOT_FOUND_LOGIN_ID(HttpStatus.NOT_FOUND, "존재하지 않는 아이디입니다."),
	WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	@Override
	public String getName() {
		return name();
	}

	@Override
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
