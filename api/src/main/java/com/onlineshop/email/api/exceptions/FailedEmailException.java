package com.onlineshop.email.api.exceptions;

public class FailedEmailException extends RuntimeException {
    public FailedEmailException() {
        super("Email was not sent");
    }
}
