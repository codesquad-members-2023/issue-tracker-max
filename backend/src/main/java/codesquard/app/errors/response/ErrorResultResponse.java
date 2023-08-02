package codesquard.app.errors.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResultResponse {
	@JsonProperty("success")
	private final boolean success;
	@JsonProperty("errorCode")
	private final ErrorResponse errorCode;

	public ErrorResultResponse(ErrorResponse errorCode) {
		this.success = false;
		this.errorCode = errorCode;
	}
}
