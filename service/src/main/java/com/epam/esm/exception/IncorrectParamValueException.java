package com.epam.esm.exception;

import java.util.Collections;
import java.util.List;

/**
 * @author Ivan Velichko
 * @date 02.11.2021 16:51
 */
public class IncorrectParamValueException extends RuntimeException {
    private List<ErrorDetails> errors;

    /**
     * Constructor a IncorrectParamValueException with the specified detail message and list of error details
     *
     * @param message the detail message
     * @param errors the list of error details
     */
    public IncorrectParamValueException(String message, List<ErrorDetails> errors) {
        super(message);
        this.errors = errors;
    }

    public IncorrectParamValueException(String message, String name) {

    }

    public IncorrectParamValueException(String message) {
    }

    public List<ErrorDetails> getErrors() {
        return errors == null ? Collections.emptyList() : Collections.unmodifiableList(errors);
    }
}
