package ispm.controller;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.Connection;




public class DBConnection {
	String sourceURL;
    public DBConnection() 
    {
    try {
        Class.forName("com.mysql.jdbc.Driver");
        sourceURL = new String("jdbc:mysql://localhost:3306/ispm");
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
    }
                
    }
    
    public Connection connect()
    {
       Connection dbcon = null;
    try {
        dbcon = (Connection) DriverManager.getConnection(sourceURL,"root","");
    } catch (SQLException ex) {
        Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
    }
    return dbcon;
    }
    
    public void close_connection (Connection dbcon)
    {
    try {
        dbcon.close();
    } catch (SQLException ex) {
        Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
}
