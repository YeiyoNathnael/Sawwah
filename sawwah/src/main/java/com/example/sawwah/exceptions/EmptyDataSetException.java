package com.example.sawwah.exceptions;

public class EmptyDataSetException extends RuntimeException {
    public EmptyDataSetException(String message) {
        super(message);
    }
}