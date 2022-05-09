package com.epam.esm.exception;

/**
 * @author Ivan Velichko
 * @date 03.10.2021 18:40
 */
public class NoSuchEntityException extends RuntimeException {

    public NoSuchEntityException() {
    }

    public NoSuchEntityException(String message) {
        super(message);
    }
}
