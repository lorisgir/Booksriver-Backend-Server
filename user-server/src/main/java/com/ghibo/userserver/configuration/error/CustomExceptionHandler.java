package com.ghibo.userserver.configuration.error;

import com.ghibo.userserver.domain.exceptions.BadRequestException;
import com.ghibo.userserver.domain.exceptions.ResourceNotFoundException;
import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return returnBasicResponse("Server Error", HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler({BadRequestException.class, AuthenticationException.class})
    public final ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return returnBasicResponse("Bad Request Error", HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler({ResourceNotFoundException.class, FeignException.NotFound.class, FeignException.class})
    public final ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        return returnBasicResponse("Not Found Error", HttpStatus.NOT_FOUND, ex);
    }

    public final ResponseEntity<Object> returnBasicResponse(String message, HttpStatus status, Exception ex) {
        List<String> details = new ArrayList<>();
        if (ex.getLocalizedMessage() != null) {
            details.add(ex.getLocalizedMessage());
        }
        ErrorResponse error = new ErrorResponse(message, details);
        return new ResponseEntity(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.toString());
        }
        ErrorResponse error = new ErrorResponse("Validation Failed", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}