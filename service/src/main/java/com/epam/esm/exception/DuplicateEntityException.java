package com.epam.esm.exception;

/**
 * @author Ivan Velichko
 * @date 09.10.2021 13:39
 */
public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException() {
    }

    public DuplicateEntityException(String message) {
        super(message);
    }
}
