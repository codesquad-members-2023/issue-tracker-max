package codesquard.app.errors.errorcode;

import org.springframework.http.HttpStatus;

public enum IssueErrorCode implements ErrorCode {

	INVALID_ISSUE_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 이슈 상태입니다."),
	NOT_FOUND_ISSUE(HttpStatus.NOT_FOUND, "이슈를 찾을 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	IssueErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	@Override
	public String getName() {
		return name();
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
