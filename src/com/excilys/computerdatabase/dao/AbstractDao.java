package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.computerdatabase.exceptions.ConnexionException;

public abstract class AbstractDao<T> {
	private Connection connection;

	abstract public List<T> getAll();

	abstract public T getById(long id);

	public Connection connect() throws ConnexionException {
		return DBConnection.getInstance().getConnection();
	}

	public void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
