package com.excilys.computerdatabase.exceptions;

import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.CompanyDao;

public class ConnectionException extends RuntimeException {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

    /**
     * Constructor of the ConnectionException class.
     *
     * @param e
     *            is the exception received
     */
    public ConnectionException(Exception e) {
        super(e);
        LOGGER.error(e.getMessage());
    }

    /**
     * Constructor of the ConnectionException class.
     *
     * @param e
     *            is the message to set.
     */
    public ConnectionException(String e) {
        super(e);
        LOGGER.error(e);
    }
}
