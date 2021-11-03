package com.epam.esm.exception;

/**
 * @author Ivan Velichko
 * @date 18.10.2021 18:25
 */
public class ConvertEntityException extends RuntimeException {
    public ConvertEntityException() {
    }

    public ConvertEntityException(String message) {
        super(message);
    }
}
