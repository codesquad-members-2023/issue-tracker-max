package com.issuetrackermax.common.exception.domain;

import java.security.SignatureException;

import org.springframework.http.HttpStatus;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtException implements CustomException {
	EXPIRED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "토큰의 기한이 만료되었습니다."),
	MALFORMED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다."),
	SIGNATURE_EXCEPTION(HttpStatus.UNAUTHORIZED, "토큰의 키가 올바르지 않습니다."),
	ILLEGAL_ARGUMENT_EXCEPTION(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

	private final HttpStatus httpStatus;
	private final String message;

	public static JwtException from(RuntimeException e) {
		if (e.getClass().equals(ExpiredJwtException.class)) {
			return JwtException.EXPIRED_JWT_EXCEPTION;
		}
		if (e.getClass().equals(MalformedJwtException.class)) {
			return JwtException.MALFORMED_JWT_EXCEPTION;
		}
		if (e.getClass().equals(SignatureException.class)) {
			return JwtException.SIGNATURE_EXCEPTION;
		}
		return JwtException.ILLEGAL_ARGUMENT_EXCEPTION;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
