package org.presents.issuetracker.global.error.statuscode;

import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum JwtErrorCode implements StatusCode {
	NOT_VALID_LOGIN_INFO(HttpStatus.UNAUTHORIZED, "로그인 정보가 유효하지 않습니다."),
	INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
	EXPIRED_JWT_TOKEN(HttpStatus.FORBIDDEN, "만료된 토큰입니다."),
	MALFORMED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "손상된 토큰입니다.");

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
