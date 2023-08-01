package codesquad.issueTracker.global;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<String>> handleCustomException(CustomException e) {
		StatusCode statusCode = e.getStatusCode();

		return ResponseEntity.status(statusCode.getStatus())
			.body(ApiResponse.fail(statusCode.getStatus(), statusCode.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {
		StatusCode statusCode = ErrorCode.REQUEST_VALIDATION_FAIL;

		List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();

		StringBuilder errorMessage = new StringBuilder();
		for (ObjectError error : objectErrors) {
			errorMessage.append(error.getDefaultMessage()).append(",");
		}
		errorMessage.deleteCharAt(errorMessage.length() - 1);

		return ResponseEntity.status(statusCode.getStatus())
			.body(ApiResponse.fail(statusCode.getStatus(), errorMessage.toString()));
	}

}

