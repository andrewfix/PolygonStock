package com.stock.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String s) {
        super(s);
    }
}
