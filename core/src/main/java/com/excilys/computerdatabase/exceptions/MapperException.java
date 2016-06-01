package com.excilys.computerdatabase.exceptions;

import org.slf4j.LoggerFactory;

public class MapperException extends RuntimeException {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MapperException.class);

    /**
     * Constructor of the MapperException class.
     *
     * @param e
     *            is the exception received
     */
    public MapperException(Exception e) {
        super(e);
        LOGGER.error(e.getMessage());
    }
}
