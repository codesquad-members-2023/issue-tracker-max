package codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.contoller.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse {

    private final int statusCode;

    public ApiResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public static ApiResponse success(HttpStatus status) {
        return new ApiResponse(status.value());
    }
}
