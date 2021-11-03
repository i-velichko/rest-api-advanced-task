package com.epam.esm.exception;

/**
 * @author Ivan Velichko
 * @date 11.10.2021 12:05
 */
public class ExtractCertificateDataException extends RuntimeException {
    public ExtractCertificateDataException() {
    }

    public ExtractCertificateDataException(String message) {
        super(message);
    }
}
