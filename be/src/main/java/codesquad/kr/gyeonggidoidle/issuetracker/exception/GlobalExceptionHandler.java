package codesquad.kr.gyeonggidoidle.issuetracker.exception;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response.ApiResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception e) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ApiResponse handleException(CustomException e) {
        return ApiResponse.fail(e.getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    public ApiResponse handleException(JwtException e) {
        JwtExceptionType jwtExceptionType = JwtExceptionType.from(e);
        return ApiResponse.fail(jwtExceptionType.getHttpstatus(), jwtExceptionType.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleException(MethodArgumentNotValidException e) {
        return ApiResponse.fail(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
