package com.example.sawwah.exceptions;

public class BackupLoadException extends BackupException {
    public BackupLoadException(String filePath) {
        super("Failed to load backup from: " + filePath);
    }
}