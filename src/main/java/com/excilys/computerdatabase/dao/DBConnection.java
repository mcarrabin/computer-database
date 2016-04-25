package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.exceptions.ConnexionException;

public class DBConnection {

    private static DBConnection instance = null;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DBConnection.class);

    private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
    private static final String LOGIN = "admincdb";
    private static final String PASSWORD = "qwerty1234";

    /**
     * Constructor of DbConnection class.
     */
    private DBConnection() {
    }

    /**
     * Method that will check if there is a current Connection. If yes, it will
     * return it. If not, it will create one and return it.
     *
     * @return the built or current DbConnection.
     */
    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    /**
     * Method that will build and return a Connection.
     *
     * @return the built connection.
     * @throws ConnexionException
     *             if there is any issue trying to open the connection.
     */
    public Connection getConnection() throws ConnexionException {
        synchronized (DBConnection.class) {
            Connection c = null;
            try {
                c = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new ConnexionException("Echec lors de l'ouverture de la connexion");
            }
            return c;
        }

    }

    /**
     * Method that will close the current connection.
     *
     * @param c
     *            current connection.
     * @throws ConnexionException
     *             which are the exceptions due to connexion issues.
     */
    public void closeConnection(Connection c) throws ConnexionException {
        synchronized (DBConnection.class) {
            try {
                c.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new ConnexionException("Echec lors de la fermeture de la connexion");
            }
        }
    }

}
