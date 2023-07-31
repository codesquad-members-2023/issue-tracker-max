package codesquad.issueTracker.global;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ApiResponse<String> handleCustomException(CustomException e) {
        StatusCode statusCode = e.getStatusCode();

        return ApiResponse.fail(statusCode.getStatus(), statusCode.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StatusCode statusCode = ErrorCode.REQUEST_VALIDATION_FAIL;

        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();

        StringBuilder errorMessage = new StringBuilder();
        for (ObjectError error : objectErrors) {
            errorMessage.append(error.getDefaultMessage()).append(",");
        }
        errorMessage.deleteCharAt(errorMessage.length() - 1);

        return ApiResponse.fail(statusCode.getStatus(), errorMessage.toString());
    }

}

