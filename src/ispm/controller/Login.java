package ispm.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Login {
	 DBConnection dbconnmanager = new DBConnection();
	    
	    public String setLogin(String username,String password)
	    {
	        Connection dbconn = null;
	        dbconn = dbconnmanager.connect();
	        boolean status = false;
	        String userstatus = null;
	        try 
	        {
	        	String uname = null;
		        String upassword = null;
		       
		            
	            Statement stmt = (Statement) dbconn.createStatement();
	            //String query = "insert into user('uname','upassword') values (username,password)";
	            String log_query = "select uname, upassword, status from user where uname='"+username+"' and upassword='"+password+"'";
	            ResultSet result = stmt.executeQuery(log_query);
	            while(result.next()){
	            
	            	 uname = result.getString("uname");
	 	             upassword = result.getString("upassword");	
	 	             userstatus = result.getString("status");
	            }	            
	           
	            if(username.equals(uname) && password.equals(upassword))
	            {
	            	status = true;
	            }
	            else 
	            	status = false;
	            
	        } 
	        catch (SQLException ex)
	        {
	            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        finally
	        {
	            dbconnmanager.close_connection(dbconn);
	        }
	       
            
	        if(status == true)
	        {
	        return userstatus;
	        }
	        else
	        	return "error!!!";
	        
	    }
}
