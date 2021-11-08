package com.epam.esm.exception;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 14.10.2021 22:07
 */
public class CustomValidationResponse {
    private List<String> errors;
    private final int errorCode;

    public CustomValidationResponse(List<String> errors, int errorCode) {
        this.errors = errors;
        this.errorCode = errorCode;
    }

    public List<String> getErrors() {
        return errors;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
