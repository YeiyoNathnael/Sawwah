package com.example.sawwah.exceptions;

public class InvalidCategoryException extends InvalidEventException {
    public InvalidCategoryException(String category) {
        super("Invalid category: " + category);
    }
}