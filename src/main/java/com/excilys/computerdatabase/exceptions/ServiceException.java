package com.excilys.computerdatabase.exceptions;

public class ServiceException extends Exception {

    /**
     * Constructor of the ServiceException class.
     *
     * @param message
     *            is the message linked to the exception.
     */
    public ServiceException(String message) {
        super(message);
    }
}
