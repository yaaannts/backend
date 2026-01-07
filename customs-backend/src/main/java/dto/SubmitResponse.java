package com.customs.dto;

public class SubmitResponse {

    private boolean success;
    private String declNo;
    private String qrImage;
    private String message;

    public SubmitResponse(boolean success, String declNo, String qrImage, String message) {
        this.success = success;
        this.declNo = declNo;
        this.qrImage = qrImage;
        this.message = message;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getDeclNo() { return declNo; }
    public String getQrImage() { return qrImage; }
    public String getMessage() { return message; }
}