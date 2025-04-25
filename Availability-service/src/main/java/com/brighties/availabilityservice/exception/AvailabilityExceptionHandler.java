package com.brighties.availabilityservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class AvailabilityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(AvailabilityExceptionHandler.class);


    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTeacherNotFoundException(
            TeacherNotFoundException ex) {

        log.warn("Teacher not found: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Teacher not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }
}
