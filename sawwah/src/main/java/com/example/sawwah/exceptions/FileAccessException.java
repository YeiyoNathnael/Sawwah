package com.example.sawwah.exceptions;

public class FileAccessException extends DataLoadException {
    public FileAccessException(String filePath) {
        super("File access error: " + filePath);
    }
}