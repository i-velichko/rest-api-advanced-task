package com.epam.esm.exception;

/**
 * @author Ivan Velichko
 * @date 04.10.2021 11:07
 */
public class CustomResponse {
    private final String message;
    private final int errorCode;

    public CustomResponse(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
