package com.excilys.computerdatabase.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.excilys.computerdatabase.exceptions.ConnectionException;
import com.zaxxer.hikari.HikariDataSource;

public enum DBManager {

    INSTANCE;
    private static ThreadLocal<Connection> thread = new ThreadLocal<>();

    private static final String PROPERTIES_FILE = "db.properties";
    private static Properties properties = new Properties();

    private static final String URL_PARAM = "url";
    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";
    private static final String DB_DRIVER_PARAM = "dbDriver";

    private static final String URL;
    private static final String LOGIN;
    private static final String PASSWORD;
    private static final String DB_DRIVER;

    private static HikariDataSource ds;

    static {

        InputStream propertiesFile = DBManager.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        try {
            properties.load(propertiesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        URL = properties.getProperty(URL_PARAM);
        LOGIN = properties.getProperty(LOGIN_PARAM);
        PASSWORD = properties.getProperty(PASSWORD_PARAM);
        DB_DRIVER = properties.getProperty(DB_DRIVER_PARAM);

        try {
            Class.forName(DB_DRIVER).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new ConnectionException(e);
        }

        ds = new HikariDataSource();
        ds.setJdbcUrl(URL);
        ds.setUsername(LOGIN);
        ds.setPassword(PASSWORD);
        ds.setMaximumPoolSize(20);
    }

    /**
     * Method that will set a connection to the thread local.
     */
    private void createConnection() {
        try {
            thread.set(ds.getConnection());
        } catch (Exception e) {
            throw new ConnectionException(e);
        }
    }

    /**
     * Method that will set the autoCommit of the local thread connection.
     *
     * @param autoCommit
     */
    public void autoCommit(boolean autoCommit) {
        try {
            thread.get().setAutoCommit(autoCommit);
        } catch (SQLException e) {
            throw new ConnectionException("Erreur while setting autoCommit");
        }
    }

    /**
     * Method that will call the current connection commit() method.
     */
    public void callCommit() {
        try {
            thread.get().commit();
        } catch (SQLException e) {
            throw new ConnectionException("Erreur while Commit");
        }
    }

    /**
     * Method that will call the current connection rollback() method.
     */
    public void callRollback() {
        try {
            thread.get().rollback();
        } catch (SQLException e) {
            throw new ConnectionException("Erreur while rollback");
        }
    }

    /**
     * Method that will build and return a Connection.
     *
     * @return the built connection.
     * @throws ConnexionException
     *             if there is any issue trying to open the connection.
     */
    public Connection getConnection() throws ConnectionException {
        if (thread.get() == null) {
            createConnection();
        }
        return thread.get();

    }

    /**
     * Method that will close the current connection.
     *
     * @param c
     *            current connection.
     * @throws ConnexionException
     *             which are the exceptions due to connexion issues.
     */
    public void closeConnection() throws ConnectionException {
        try {
            if (thread.get() != null) {
                thread.get().close();
            }
            thread.remove();
        } catch (Exception e) {
            throw new ConnectionException(e);
        }
    }

}
