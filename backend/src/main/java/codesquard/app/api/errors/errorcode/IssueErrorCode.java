package codesquard.app.api.errors.errorcode;

import org.springframework.http.HttpStatus;

import lombok.ToString;

@ToString
public enum IssueErrorCode implements ErrorCode {

	INVALID_ISSUE_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 이슈 상태입니다."),
	NOT_FOUND_ISSUE(HttpStatus.NOT_FOUND, "존재하지 않는 이슈입니다."),
	FORBIDDEN_ISSUE(HttpStatus.FORBIDDEN, "접근 권한이 없는 이슈입니다."),
	FORBIDDEN_USER_REACTION(HttpStatus.FORBIDDEN, "접근 권한이 없는 사용자 반응입니다.");

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
