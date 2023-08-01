package codesquad.issueTracker.global;

import org.springframework.http.HttpStatus;

public enum ErrorCode implements StatusCode {

	REQUEST_VALIDATION_FAIL(HttpStatus.BAD_REQUEST),

	// -- [USER] -- //
	ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다."),
	NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "해당하는 유저가 없습니다."),
	FAILED_LOGIN_USER(HttpStatus.BAD_REQUEST, "로그인에 실패했습니다. 아이디, 비밀번호를 다시 입력해주세요. ");

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
}
