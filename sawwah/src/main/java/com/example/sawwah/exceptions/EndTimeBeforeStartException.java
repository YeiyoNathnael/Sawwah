package com.example.sawwah.exceptions;

public class EndTimeBeforeStartException extends InvalidEventTimeException {
    public EndTimeBeforeStartException() {
        super("End time cannot be before start time.");
    }
}