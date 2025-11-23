package com.example.sawwah.exceptions;

public class BackupLoadException_Sec33_G11 extends BackupException_Sec33_G11 {
    public BackupLoadException_Sec33_G11(String filePath) {
        super("Failed to load backup from: " + filePath);
    }
}