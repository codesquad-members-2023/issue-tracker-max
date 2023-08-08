package codesquard.app.api.errors.errorcode;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum JwtTokenErrorCode implements ErrorCode {

	NOT_MATCH_REFRESHTOKEN(HttpStatus.BAD_REQUEST, "Refreshtoken이 일치하지 않습니다.");

	private HttpStatus httpStatus;
	private String message;

	JwtTokenErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	@Override
	public String getName() {
		return name();
	}
}
