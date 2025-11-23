package com.example.sawwah.exceptions;

public class BackupSaveException_Sec33_G11 extends BackupException_Sec33_G11 {
    public BackupSaveException_Sec33_G11(String filePath) {
        super("Failed to save backup to: " + filePath);
    }
}