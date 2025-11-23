package com.example.sawwah.exceptions;

public class DuplicateEventException_Sec33_G11 extends InvalidEventException_Sec33_G11 {
    public DuplicateEventException_Sec33_G11(String eventName) {
        super("Duplicate event: " + eventName);
    }
}