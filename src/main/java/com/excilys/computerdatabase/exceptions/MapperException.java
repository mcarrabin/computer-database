package com.excilys.computerdatabase.exceptions;

public class MapperException extends Exception {
    private String message;

    /**
     * Constructor of the MapperException class.
     *
     * @param message
     *            is the message linked to the exception.
     */
    public MapperException(String message) {
        super(message);
    }
}
