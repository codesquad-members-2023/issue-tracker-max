package codesquard.app.errors.response;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import codesquard.app.errors.errorcode.ErrorCode;

public class ErrorResponse {

	@JsonProperty("name")
	private final String name;
	@JsonProperty("code")
	private final int code;
	@JsonProperty("httpStatus")
	private final HttpStatus httpStatus;
	@JsonProperty("errorMessage")
	private final String errorMessage;

	@JsonProperty("errors")
	@JsonInclude(value = Include.NON_EMPTY)
	private final List<ValidationError> errors;

	public ErrorResponse(ErrorCode errorCode,
		List<ValidationError> errors) {
		this.name = errorCode.getName();
		this.code = errorCode.getHttpStatus().value();
		this.httpStatus = errorCode.getHttpStatus();
		this.errorMessage = errorCode.getMessage();
		this.errors = errors;
	}

	public static class ValidationError {

		@JsonProperty("field")
		private final String field;
		@JsonProperty("message")
		private final String message;

		public ValidationError(String field, String message) {
			this.field = field;
			this.message = message;
		}

		public static ValidationError of(FieldError fieldError) {
			return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
		}
	}
}
