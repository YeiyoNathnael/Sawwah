package com.example.sawwah.exceptions;

public class EmptyEventNameException extends InvalidEventException {
    public EmptyEventNameException() {
        super("Event name cannot be empty.");
    }
}