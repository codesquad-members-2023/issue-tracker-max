package codesquad.issueTracker.global.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

	private final HttpStatus status;
	private final T message;

	public static <T> ApiResponse<T> success(HttpStatus status, T message) {
		return new ApiResponse<>(status, message);
	}

	public static <T> ApiResponse<T> fail(HttpStatus status, T message) {
		return new ApiResponse<>(status, message);
	}
}
