package com.example.sawwah.exceptions;

public class InvalidLocationException_Sec33_G11 extends InvalidEventException_Sec33_G11 {
    public InvalidLocationException_Sec33_G11(String location) {
        super("Invalid location: " + location);
    }
}