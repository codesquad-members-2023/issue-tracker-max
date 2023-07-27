package codesquard.app.errors.exception;

import codesquard.app.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RestApiException extends RuntimeException {

	private final ErrorCode errorCode;

	public RestApiException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
