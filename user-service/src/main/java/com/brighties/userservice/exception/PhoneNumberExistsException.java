package com.brighties.userservice.exception;

public class PhoneNumberExistsException extends RuntimeException {
    public PhoneNumberExistsException(String message) {
        super(message);
    }
}
