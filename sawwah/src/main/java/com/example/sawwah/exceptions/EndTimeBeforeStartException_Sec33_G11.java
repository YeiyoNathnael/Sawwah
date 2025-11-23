package com.example.sawwah.exceptions;

public class EndTimeBeforeStartException_Sec33_G11 extends InvalidEventTimeException_Sec33_G11 {
    public EndTimeBeforeStartException_Sec33_G11() {
        super("End time cannot be before start time.");
    }
}