package codesquard.app.errors.response;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

import codesquard.app.errors.errorcode.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = {"name", "httpStatus", "errorMessage"})
@ToString
public class ErrorResponse {

	private final String name;
	private final HttpStatus httpStatus;
	private final String errorMessage;

	@JsonInclude(value = Include.NON_EMPTY)
	private final List<ValidationError> errors;

	public ErrorResponse(ErrorCode errorCode,
		List<ValidationError> errors) {
		this.name = errorCode.getName();
		this.httpStatus = errorCode.getHttpStatus();
		this.errorMessage = errorCode.getMessage();
		this.errors = errors;
	}

	@RequiredArgsConstructor
	@Getter
	@EqualsAndHashCode(of = {"field", "message"})
	@ToString
	public static class ValidationError {

		private final String field;
		private final String message;

		public static ValidationError of(FieldError fieldError) {
			return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
		}
	}
}
