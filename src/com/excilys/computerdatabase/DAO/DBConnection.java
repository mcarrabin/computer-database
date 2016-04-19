package com.excilys.computerdatabase.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	
	private Connection conn;
	private static DBConnection _instance = null;

	private final static String URL = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private final static String LOGIN = "admincdb";
	private final static String PASSWORD = "qwerty1234";

	private DBConnection(){
		try {
			this.conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this._instance = this;
	}
	
	synchronized public static DBConnection getInstance(){
		if(_instance == null){
			new DBConnection();
		}	
		return _instance;
	}
	
	public Connection getConnection(){
		return this.conn;
	}
	
	public void closeConnection(){
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
