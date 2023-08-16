package codesquard.app.api.errors.errorcode;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum OauthErrorCode implements ErrorCode {
	BAD_AUTHORIZATION_CODE(HttpStatus.BAD_REQUEST, "잘못된 인가코드입니다.");

	private HttpStatus httpStatus;
	private String message;

	OauthErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	@Override
	public String getName() {
		return name();
	}
}
