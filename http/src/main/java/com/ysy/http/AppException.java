package com.ysy.http;

/**
 * Created by Stay on 6/7/15.
 * Powered by www.stay4it.com
 */
public class AppException extends Exception {

    public int statusCode;
    public String responseMessage;

    public enum ErrorType {CANCEL,TIMEOUT, SERVER, JSON, IO, FILE_NOT_FOUND, UPLOAD, MANUAL}
    public ErrorType type;

    public AppException(int status, String responseMessage) {
        super(responseMessage);
        this.type = ErrorType.SERVER;
        this.statusCode = status;
        this.responseMessage = responseMessage;
    }

    public AppException(ErrorType type, String detailMessage) {
        super(detailMessage);
        this.type = type;
    }
}
