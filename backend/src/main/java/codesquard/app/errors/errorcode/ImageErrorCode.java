package codesquard.app.errors.errorcode;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ImageErrorCode implements ErrorCode {

	MAX_UPLOAD_SIZE_EXCEED(HttpStatus.BAD_REQUEST, "파일의 최대 첨부 용량을 초과하였습니다."),
	EMPTY_FILE(HttpStatus.BAD_REQUEST, "파일이 비어있습니다.");

	private final HttpStatus httpStatus;
	private final String message;

	ImageErrorCode(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	@Override
	public String getName() {
		return name();
	}

}
