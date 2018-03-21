package com.example.io.exception;

public class FileMissingException extends RuntimeException {
    
    public FileMissingException(String message) {
        super(message);
    }
}
