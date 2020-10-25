package com.anz.wholesale.exceptions;


public class TransactionNotExistException extends RuntimeException {
    public TransactionNotExistException(String message) {
        super(message);
    }
}
