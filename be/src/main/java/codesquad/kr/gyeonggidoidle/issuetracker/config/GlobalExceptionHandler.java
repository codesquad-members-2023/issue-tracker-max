package codesquad.kr.gyeonggidoidle.issuetracker.config;

import codesquad.kr.gyeonggidoidle.issuetracker.domain.issue.controller.response.ApiResponse;
import codesquad.kr.gyeonggidoidle.issuetracker.exception.CustomException;
import codesquad.kr.gyeonggidoidle.issuetracker.exception.JwtExceptionType;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        return new ResponseEntity(ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleException(CustomException e) {
        return new ResponseEntity<>(ApiResponse.fail(e.getHttpStatus(), e.getMessage()), e.getHttpStatus());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse> handleException(JwtException e) {
        JwtExceptionType jwtExceptionType = JwtExceptionType.from(e);
        return new ResponseEntity<>(ApiResponse.fail(jwtExceptionType.getHttpstatus(), jwtExceptionType.getMessage()),
                jwtExceptionType.getHttpstatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(ApiResponse.fail(HttpStatus.BAD_REQUEST, "올바른 입력 값이 아닙니다."),
                HttpStatus.BAD_REQUEST);
    }
}
