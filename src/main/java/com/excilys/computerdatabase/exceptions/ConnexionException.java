package com.excilys.computerdatabase.exceptions;

import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.CompanyDao;

public class ConnexionException extends RuntimeException {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

    /**
     * Constructor of the ConnexionException class.
     *
     * @param e
     *            is the exception received
     */
    public ConnexionException(Exception e) {
        super(e);
        LOGGER.error(e.getMessage());
    }
}
