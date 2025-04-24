package com.brighties.reservationservice.exception;

public class AvailabilitySlotIsAlreadyReservedException extends RuntimeException {
    public AvailabilitySlotIsAlreadyReservedException(String message) {
        super(message);
    }
}
