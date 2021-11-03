package com.epam.esm.exception;

/**
 * @author Ivan Velichko
 * @date 16.10.2021 15:29
 */
public class NoSuchParameterException extends RuntimeException {

    public NoSuchParameterException() {
    }

    public NoSuchParameterException(String message) {
        super(message);
    }
}
