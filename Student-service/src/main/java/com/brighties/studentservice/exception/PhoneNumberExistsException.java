package com.brighties.studentservice.exception;

public class PhoneNumberExistsException extends RuntimeException {
    public PhoneNumberExistsException(String message) {
        super(message);
    }
}
