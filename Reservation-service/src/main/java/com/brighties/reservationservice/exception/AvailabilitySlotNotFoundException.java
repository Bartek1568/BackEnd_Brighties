package com.brighties.reservationservice.exception;

public class AvailabilitySlotNotFoundException extends RuntimeException {
    public AvailabilitySlotNotFoundException(String message) {
        super(message);
    }
}
