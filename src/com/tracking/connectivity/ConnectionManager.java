package com.tracking.connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Statement;

/**
 * db connection class
 */
public class ConnectionManager {

	private static DataSource dataSource;

	private static Connection connection;
	private Statement stmnt;
	private ResultSet rst;

	private final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/sfts?autoReconnect=true&useSSL=false";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "root";

	private static ConnectionManager instance;

	private ConnectionManager() {

	}

	public static ConnectionManager getInstance() {
		if (instance == null ) {
			instance = new ConnectionManager();
		}
		return instance;
	}

	private static void initialize() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver class loaded successfully");
		} catch (ClassNotFoundException cnfex) {
			System.err.println("Driver Class not found  ");
			cnfex.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
			System.out.println("connection established " + connection);

		} catch (Exception e) {
			System.err.println("Could not establish the connection ");
			e.printStackTrace();
		}
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public Connection getConnection() throws SQLException {
		System.out.println("getConnection() ==== " + connection);
		if (connection == null || connection.isClosed()) {
			initialize();
		}
		return connection;
	}

	/**
	 * Close the connection
	 *
	 * @param connection
	 *            the connection
	 */
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, e);
			}
		}
	}

	public static void main(String[] args) {

		initialize();

	}

}
