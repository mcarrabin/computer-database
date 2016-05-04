package com.excilys.computerdatabase.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import com.excilys.computerdatabase.exceptions.ConnectionException;
import com.zaxxer.hikari.HikariDataSource;

public enum DBConnection {

    INSTANCE;
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

        InputStream propertiesFile = DBConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
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
    }

    /**
     * Method that will build and return a Connection.
     *
     * @return the built connection.
     * @throws ConnexionException
     *             if there is any issue trying to open the connection.
     */
    public Connection getConnection() throws ConnectionException {
        try {
            return ds.getConnection();
        } catch (Exception e) {
            throw new ConnectionException(e);
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
    public void closeConnection(Connection c) throws ConnectionException {
        try {
            c.close();
        } catch (Exception e) {
            throw new ConnectionException(e);
        }
    }

}
