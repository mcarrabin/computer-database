package com.excilys.computerdatabase.exceptions;

import org.slf4j.LoggerFactory;

public class DaoException extends RuntimeException {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DaoException.class);

    /**
     * Constructor of the DaoException class.
     *
     * @param e
     *            is the exception received
     */
    public DaoException(Exception e) {
        super(e);
        LOGGER.error(e.getMessage());
    }

    /**
     * Constructor of the DaoException class.
     *
     * @param e
     *            is the exception received
     */
    public DaoException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
