package com.example.hanghaeplus.common.error.exception;

import com.example.hanghaeplus.common.error.ErrorCode;
import com.example.hanghaeplus.common.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.example.hanghaeplus.common.error.ErrorCode.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<?> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = ErrorResponse.of(errorCode, e.getErrors());
        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        final ErrorResponse response = ErrorResponse.of(INPUT_VALUE_INVALID, e.getParameterName());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(INPUT_VALUE_INVALID, e.getBindingResult());
        return new ResponseEntity<>(response, BAD_REQUEST);
    }

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        final ErrorResponse response = ErrorResponse.of(e);
        return new ResponseEntity<>(response, BAD_REQUEST);
    }



}
