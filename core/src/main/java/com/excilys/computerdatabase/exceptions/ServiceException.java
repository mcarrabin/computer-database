package com.excilys.computerdatabase.exceptions;

import org.slf4j.LoggerFactory;

public class ServiceException extends RuntimeException {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ServiceException.class);

    /**
     * Constructor of the ServiceException class.
     *
     * @param e
     *            is the exception received
     */
    public ServiceException(Exception e) {
        super(e);
        LOGGER.error(e.getMessage());
    }

    /**
     * Constructor of the Service Exception class
     *
     * @param e
     *            is the message of the Exception to throw.
     */
    public ServiceException(String e) {
        super(e);
        LOGGER.error(e);
    }
}
