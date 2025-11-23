package com.example.sawwah.exceptions;

public class InvalidLocationException extends InvalidEventException {
    public InvalidLocationException(String location) {
        super("Invalid location: " + location);
    }
}