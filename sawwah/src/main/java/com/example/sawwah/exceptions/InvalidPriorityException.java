package com.example.sawwah.exceptions;

public class InvalidPriorityException extends InvalidEventException {
    public InvalidPriorityException(int priority) {
        super("Invalid priority: " + priority + ". Must be between 1 and 5.");
    }
}