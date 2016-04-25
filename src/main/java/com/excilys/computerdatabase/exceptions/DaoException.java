package com.excilys.computerdatabase.exceptions;

public class DaoException extends Exception {
    /**
     * Constructor of the DaoException class.
     *
     * @param message
     *            is the message linked to the exception.
     */
    public DaoException(String message) {
        super(message);
    }
}
