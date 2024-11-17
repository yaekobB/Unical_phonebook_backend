package com.api.user_management.shared.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(PhonebookExistsException.class)
    public ResponseEntity<?> studentExistsException(PhonebookExistsException st) {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.setError(HttpStatus.INTERNAL_SERVER_ERROR.name());
        errorDetails.setMessage(st.getMessage());
        errorDetails.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(errorDetails,HttpStatus.OK);

    }
    @ExceptionHandler(PhonebookNotFoundException.class)
    public ResponseEntity<?> studentNotFoundException(PhonebookNotFoundException st) {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetails.setError(HttpStatus.NOT_FOUND.name());
        errorDetails.setMessage(st.getMessage());
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(PhonebookInternalServerException.class)
    public ResponseEntity<?> methodValidation(PhonebookInternalServerException st) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.setError(HttpStatus.INTERNAL_SERVER_ERROR.name());
        errorDetails.setMessage(st.getMessage());
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);

    }
    
    @ExceptionHandler(PhonebookSuccessfulException.class)
    public ResponseEntity<?> Successful(PhonebookSuccessfulException st) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.ACCEPTED.value());
        errorDetails.setError("");
        errorDetails.setMessage(st.getMessage());
        errorDetails.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(errorDetails,HttpStatus.ACCEPTED);

    }
}
