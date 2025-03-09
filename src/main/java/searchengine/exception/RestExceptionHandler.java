package searchengine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CustomException.class)
    ResponseEntity<ErrorResponseBody> handleException(CustomException ex){
        return buildResponse(HttpStatus.BAD_REQUEST, ex);
    }

    private ResponseEntity<ErrorResponseBody> buildResponse(HttpStatus httpStatus, Exception ex) {
        return ResponseEntity.status(httpStatus)
                .body(ErrorResponseBody.builder()
                        .result(false)
                        .error(ex.getMessage())
                        .build());
    }
}
