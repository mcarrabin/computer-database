package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.exceptions.ConnectionException;
import com.zaxxer.hikari.HikariDataSource;

@Component("dbManager")
public class DBManager {

    @Autowired
    @Qualifier("dataSource")
    private HikariDataSource ds;

    public HikariDataSource getDataSource() {
        return ds;
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
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }

    }

}
