package com.example.sawwah.exceptions;

public class InvalidPriorityException_Sec33_G11 extends InvalidEventException_Sec33_G11 {
    public InvalidPriorityException_Sec33_G11(int priority) {
        super("Invalid priority: " + priority + ". Must be between 1 and 5.");
    }
}