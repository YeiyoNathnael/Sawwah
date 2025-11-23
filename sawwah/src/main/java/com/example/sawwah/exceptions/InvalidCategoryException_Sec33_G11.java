package com.example.sawwah.exceptions;

public class InvalidCategoryException_Sec33_G11 extends InvalidEventException_Sec33_G11 {
    public InvalidCategoryException_Sec33_G11(String category) {
        super("Invalid category: " + category);
    }
}