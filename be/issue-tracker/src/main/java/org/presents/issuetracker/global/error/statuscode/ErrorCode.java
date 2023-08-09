package org.presents.issuetracker.global.error.statuscode;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode implements StatusCode {
	// 예시. 필요한 에러가 있을 때 마다 아래에 추가해서 사용합니다.
	// 400 Bad Request
	VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "유효하지 않은 형식입니다."),
	// 404 Not Found
	PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다."),
	//500 Internal Server Error
	INTERNAL_SERVER_ERROR_DB(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 에러입니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다.");
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
