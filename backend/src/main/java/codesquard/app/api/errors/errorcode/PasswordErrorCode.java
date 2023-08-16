package codesquard.app.api.errors.errorcode;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum PasswordErrorCode implements ErrorCode {

	FAIL_PASSWORD_ENCRYPT(HttpStatus.INTERNAL_SERVER_ERROR, "패스워드 암호화에 실패하였습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	PasswordErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	@Override
	public String getName() {
		return name();
	}
}
