package com.example.spring6restmvc.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * @author : Taariq
 * @mailto : tghansar@gmail.com
 * @created : 2024/12/09, Mon, 11:48
 */


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity generalExceptionHandler(Exception e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException e) {

        List errorList = e.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException e) {

        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();

        if (e.getCause().getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) e.getCause().getCause();
            List errors = cve.getConstraintViolations().stream()
                    .map(constraintViolation -> {
                       Map<String, String> errorMap = new HashMap<>();
                       errorMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                       return errorMap;
                    })
                    .toList();
            return responseEntity.body(errors);
        }
        return responseEntity.build();
    }
}
