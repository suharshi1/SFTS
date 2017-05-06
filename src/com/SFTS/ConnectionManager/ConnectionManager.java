package com.SFTS.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * db connection class
 */
public class ConnectionManager {

    private DataSource dataSource;

    /**
     * The Class ConnectionLoader.
     */
    private static class ConnectionLoader {

        private static ConnectionManager connectionManager = new ConnectionManager();
    }

    /**
     * Instantiates a new connection manager.
     */
    private ConnectionManager() {
       /* Context c;
        try {
            c = new InitialContext();
            dataSource = (DataSource) c
                    .lookup("java:comp/env/tourbuddy_schema_pool");
        } catch (NamingException e) {
            Logger.getLogger(ConnectionManager.class.getName()).log(
                    Level.SEVERE, null, e);
        }*/
    }

    /**
     * Gets the single instance of ConnectionManager.
     *
     * @return single instance of ConnectionManager
     */
    public static ConnectionManager getInstance() {
        return ConnectionLoader.connectionManager;
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        Connection connection = null;
       /* try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            Logger.getLogger(ConnectionManager.class.getName()).log(
                    Level.SEVERE, null, e);
        }
*/
        try {
			Class.forName("com.mysql.jdbc.Driver");
	        
	        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tourbuddy","root", "1234");
System.out.println("connection established "+connection);
        
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return connection;
    }

    /**
     * Close the connection
     *
     * @param connection the connection
     */
    public void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(ConnectionManager.class.getName()).log(
                        Level.SEVERE, null, e);
            }
        }
    }

}
