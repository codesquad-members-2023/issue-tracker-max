package codesquad.issueTracker.global;

import org.springframework.http.HttpStatus;

public enum ErrorCode implements StatusCode {

    REQUEST_VALIDATION_FAIL(HttpStatus.BAD_REQUEST),

    // -- [USER] -- //
    ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다.");

    private HttpStatus status;
    private String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    ErrorCode(HttpStatus status) {
        this.status = status;
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
