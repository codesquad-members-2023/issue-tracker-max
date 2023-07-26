package kr.codesquad.issuetracker.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

	DUPLICATED_LOGIN_ID(400, "중복된 로그인 아이디가 존재합니다."),
	USER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
	FAILED_LOGIN(400, "비밀번호가 일치하지 않습니다."),
	FAILED_ENCRYPTION(500, "해싱 인코딩에 실패했습니다.");

	private final int statusCode;
	private final String message;

	ErrorCode(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
}
