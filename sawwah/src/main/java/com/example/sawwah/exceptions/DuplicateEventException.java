package com.example.sawwah.exceptions;

public class DuplicateEventException extends InvalidEventException {
    public DuplicateEventException(String eventName) {
        super("Duplicate event: " + eventName);
    }
}