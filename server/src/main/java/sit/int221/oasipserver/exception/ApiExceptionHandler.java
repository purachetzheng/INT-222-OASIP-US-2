package sit.int221.oasipserver.exception;

import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sit.int221.oasipserver.exception.type.ApiNotFoundException;
import sit.int221.oasipserver.exception.type.ApiRequestException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        List<ErrorDetail> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ErrorDetail(fieldName, errorMessage));
        });
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                "Event attributes validation failed!",
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z")),
                errors
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        // 1. Create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, badRequest);
    }
    @ExceptionHandler(value = {ApiNotFoundException.class})
    public ResponseEntity<Object> handleApiNotFoundException(ApiNotFoundException e){
        // 1. Create payload containing exception details
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // 2. Return response entity
        return new ResponseEntity<>(apiException, notFound);
    }

    @ExceptionHandler(value = PasswordException.class)
    public ResponseEntity<Object> exception(PasswordException e) {
        HttpStatus unAuthorized = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                "password DOES NOT match",
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, unAuthorized);
    }

    @ExceptionHandler(value = ForbiddenException.class)
    public ResponseEntity<Object> forbidden(ForbiddenException e) {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        ApiException apiException = new ApiException(
                "FORBIDDEN",
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, forbidden);
    }

}
//https://www.youtube.com/watch?v=PzK4ZXa2Tbc