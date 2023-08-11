package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private final int statusCode;
    private final T data;

    private ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public static ApiResponse success(HttpStatus status) {
        return new ApiResponse(status.value(), null);
    }

    public static ApiResponse exception(HttpStatus status, String data) {
        return new ApiResponse<>(status.value(), data);

    }
}
