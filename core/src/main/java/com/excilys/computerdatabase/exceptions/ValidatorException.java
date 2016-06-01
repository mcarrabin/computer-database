package com.excilys.computerdatabase.exceptions;

import org.slf4j.LoggerFactory;

public class ValidatorException extends RuntimeException {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ValidatorException.class);

    /**
     * Constructor of the ServiceException class.
     *
     * @param e
     *            is the exception received
     */
    public ValidatorException(Exception e) {
        super(e);
        LOGGER.error(e.getMessage());
    }

    /**
     * Constructor which calls the RuntimeException constructor.
     *
     * @param message
     *            is the exception's message.
     */
    public ValidatorException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
