package com.excilys.computerdatabase.mappers;

import java.sql.ResultSet;
import java.util.List;

import com.excilys.computerdatabase.exceptions.MapperException;

public interface Mapper<T> {

    /**
     * Method which build on object based on the result parameter (one line of a
     * resultSet).
     *
     * @param result
     *            is the resultSet returned by a query
     * @return a ArrayList of objects
     * @throws MapperException
     *             Throws a MapperException
     */
    T mapUnique(ResultSet result) throws MapperException;

    /**
     * Method which builds a list of objects by looping on a resultSet.
     *
     * @param result
     *            is the resultSet returned by a query
     * @return a ArrayList of objects
     * @throws MapperException
     *             Throws a MapperException
     */
    List<T> mapAll(ResultSet result) throws MapperException;
}
