package com.example.sawwah.exceptions;

public class EventNotFoundException_Sec33_G11 extends RuntimeException {
    public EventNotFoundException_Sec33_G11(String eventName) {
        super("Event not found: " + eventName);
    }
}