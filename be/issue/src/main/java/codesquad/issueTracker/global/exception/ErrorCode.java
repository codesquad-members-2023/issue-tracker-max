package codesquad.issueTracker.global.exception;

import java.time.DateTimeException;

import org.springframework.http.HttpStatus;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

public enum ErrorCode implements StatusCode {

	REQUEST_VALIDATION_FAIL(HttpStatus.BAD_REQUEST),

	// -- [Common] -- ]
	DB_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "DB 서버 오류"),

	// -- [OAuth] -- //
	NOT_SUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 로그인 방식입니다."),
	GITHUB_LOGIN_USER(HttpStatus.BAD_REQUEST, "이미 깃허브로 로그인한 유저입니다"),

	// -- [JWT] -- //
	NOT_FOUND_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "해당하는 리프레시 토큰을 찾을 수 없습니다."),
	MALFORMED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰입니다."),
	EXPIRED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
	SIGNATURE_EXCEPTION(HttpStatus.UNAUTHORIZED, "JWT의 서명이 올바르지 않습니다."),
	UNSUPPORTED_JWT_EXCEPTION(HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."),
	ILLEGAL_ARGUMENT_EXCEPTION(HttpStatus.UNAUTHORIZED, "잘못된 인자입니다."),

	// -- [USER] -- //
	ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다."),
	NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "해당하는 유저가 없습니다."),
	FAILED_LOGIN_USER(HttpStatus.BAD_REQUEST, "로그인에 실패했습니다. 아이디, 비밀번호를 다시 입력해주세요. "),

	// -- [Comment] -- //
	NOT_EXIST_COMMENT(HttpStatus.BAD_REQUEST, "존재하지 않는 댓글입니다."),
	ALREADY_DELETED_COMMENT(HttpStatus.BAD_REQUEST, "이미 삭제된 댓글입니다."),

	// 마일스톤
	INVALIDATE_DATE(HttpStatus.BAD_REQUEST, "현재 날짜보다 이전 날짜 입니다."),
	NOT_FOUND_DATE(HttpStatus.BAD_REQUEST, "유효하지 않은 날짜 입니다."),
	NOT_FOUND_MILESTONE(HttpStatus.BAD_REQUEST, "마일스톤을 찾을 수 없습니다."),
	ILLEGAL_STATUS_MILESTONE(HttpStatus.BAD_REQUEST, "올바르지 않은 상태 입력 입니다.");

	private HttpStatus status;
	private String message;

	ErrorCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	ErrorCode(HttpStatus status) {
		this.status = status;
	}

	@Override
	public HttpStatus getStatus() {
		return this.status;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	public static StatusCode from(RuntimeException e) {
		if (e instanceof MalformedJwtException) {
			return ErrorCode.MALFORMED_JWT_EXCEPTION;
		}
		if (e instanceof ExpiredJwtException) {
			return ErrorCode.EXPIRED_JWT_EXCEPTION;
		}
		if (e instanceof SignatureException) {
			return ErrorCode.SIGNATURE_EXCEPTION;
		}
		if (e instanceof UnsupportedJwtException) {
			return ErrorCode.UNSUPPORTED_JWT_EXCEPTION;
		}
		if (e instanceof DateTimeException) {
			return ErrorCode.NOT_FOUND_DATE;
		}
		return ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION;
	}
}
