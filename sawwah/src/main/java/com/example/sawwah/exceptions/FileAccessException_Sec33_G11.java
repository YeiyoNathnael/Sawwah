package com.example.sawwah.exceptions;

public class FileAccessException_Sec33_G11 extends DataLoadException_Sec33_G11 {
    public FileAccessException_Sec33_G11(String filePath) {
        super("File access error: " + filePath);
    }
}