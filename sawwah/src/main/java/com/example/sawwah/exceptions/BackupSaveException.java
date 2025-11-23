package com.example.sawwah.exceptions;

public class BackupSaveException extends BackupException {
    public BackupSaveException(String filePath) {
        super("Failed to save backup to: " + filePath);
    }
}