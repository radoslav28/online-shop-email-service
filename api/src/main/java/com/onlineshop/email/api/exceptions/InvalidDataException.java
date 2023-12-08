package com.onlineshop.email.api.exceptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException() {
        super("Invalid data");
    }
}
