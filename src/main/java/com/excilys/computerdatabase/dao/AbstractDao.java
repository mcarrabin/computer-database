package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.exceptions.DaoException;

public interface AbstractDao<T> {

    /**
     * Method that will get every objects in the database.
     *
     * @return all the objects contained in the database.
     * @throws DaoException
     *             which are exceptions thrown by Dao classes.
     */
    List<T> getAll() throws DaoException;

    /**
     * Method that will get an Object based on id match.
     *
     * @param id
     *            of the object to look for.
     * @return the object if founded.
     * @throws DaoException
     *             which are exceptions thrown by Dao classes.
     */
    T getById(long id) throws DaoException;

    /**
     * Method that will delete an object based on the id.
     *
     * @param id
     *            is the id of the object to delete.
     * @return true if deletion succeed else false.
     * @throws DaoException
     *             if something went wrong.
     */
    void delete(T t) throws DaoException;

    /**
     * Method that will create an object in BDD.
     *
     * @param t
     *            is the object to create.
     * @throws UnsupportedOperationException
     *             if the method is not overriden (Company object for example).
     */
    default void create(final T t) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method that will update an object in BDD.
     *
     * @param t
     *            is the object to update.
     * @throws UnsupportedOperationException
     *             if the method is not overriden (Company object for example).
     */
    default void update(final T t) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method that will get Computer objects to build a page.
     *
     * @param nbreLine
     *            is the number of elements by page.
     * @param numPage
     *            is the page number wanted.
     * @param search
     *            is the search filter.
     * @param orderBy
     *            is the order by paramater.
     * @return the built page.
     */
    default Page<Computer> getByPage(int nbreLine, int numPage, String search, String orderBy) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method that will throw a count(*) request in the DB to know how many
     * computers are send back by server.
     *
     * @param search
     *            is the search parameter.
     * @return the number of Computer objects found in the DB.
     */
    default long getNumTotalComputer(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method that will be overriden only in the computerDao. It is called when
     * the user wanna delete a company and every linked computers.
     *
     * @param id
     *            is the id of the company to delete.
     */
    default void deleteByCompany(long id) {
        throw new UnsupportedOperationException();
    }
}
