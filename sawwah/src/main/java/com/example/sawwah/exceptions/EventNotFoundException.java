package com.example.sawwah.exceptions;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String eventName) {
        super("Event not found: " + eventName);
    }
}