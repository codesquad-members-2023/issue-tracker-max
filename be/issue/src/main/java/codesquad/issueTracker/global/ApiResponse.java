package codesquad.issueTracker.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

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
