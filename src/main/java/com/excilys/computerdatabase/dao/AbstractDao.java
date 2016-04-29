package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.exceptions.ConnectionException;
import com.excilys.computerdatabase.exceptions.DaoException;

public interface AbstractDao<T> {

    /**
     * Method that will get every objects in the database.
     *
     * @return all the objects contained in the database.
     * @throws DaoException
     *             which are exceptions thrown by Dao classes.
     */
    public abstract List<T> getAll() throws DaoException;

    /**
     * Method that will get an Object based on id match.
     *
     * @param id
     *            of the object to look for.
     * @return the object if founded.
     * @throws DaoException
     *             which are exceptions thrown by Dao classes.
     */
    public abstract T getById(long id) throws DaoException;

    /**
     * Method that will get the current connection and return it.
     *
     * @return the current selection.
     * @throws ConnexionException
     *             is an exception thrown by the DBConnection class.
     */
    public default Connection connect() {
        try {
            return DBConnection.INSTANCE.getConnection();
        } catch (ConnectionException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Method that will close the connexion of the current instance of Dao.
     *
     * @throws ConnexionException
     *             which is the exception related to the connection.
     */
    public default void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
