package com.brighties.reservationservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ReservationExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ReservationExceptionHandler.class);

    @ExceptionHandler(AvailabilitySlotIsAlreadyReservedException.class)
    public ResponseEntity<Map<String, String>> handleAvailabilitySlotIsAlreadyReservedException(
            AvailabilitySlotIsAlreadyReservedException ex) {

        log.warn("Slot already reserved: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "This availability slot is already reserved.");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AvailabilitySlotNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAvailabilitySlotNotFoundException(
            AvailabilitySlotNotFoundException ex) {

        log.warn("Availability slot not found: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Availability slot not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleStudentNotFoundException(
            StudentNotFoundException ex) {

        log.warn("Student not found: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Student not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTeacherNotFoundException(
            TeacherNotFoundException ex) {

        log.warn("Teacher not found: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Teacher not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }
}
