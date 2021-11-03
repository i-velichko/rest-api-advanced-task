package com.epam.esm.exception;

/**
 * @author Ivan Velichko
 * @date 02.11.2021 16:52
 */
public class ErrorDetails {
    private String messageKey;
    private String incorrectParameter;
    private int ErrorCode;

    public ErrorDetails(String messageKey, String incorrectParameter, int errorCode) {
        this.messageKey = messageKey;
        this.incorrectParameter = incorrectParameter;
        ErrorCode = errorCode;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getIncorrectParameter() {
        return incorrectParameter;
    }

    public int getErrorCode() {
        return ErrorCode;
    }
}
