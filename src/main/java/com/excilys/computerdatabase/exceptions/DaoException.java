package com.excilys.computerdatabase.exceptions;

import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.CompanyDao;

public class DaoException extends RuntimeException {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

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
