package io.vaibhav.auto.parker.exception;

public enum ErrorCode {
    INVALID_VALUE("{variable} value is incorrect"),
    PROCESSING_ERROR("Processing Error ");

    private String message = "";

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
