package com.tracking.connectivity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.hibernate.user.UserManage;

import java.sql.Statement;

import java.sql.CallableStatement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.tracking.domain.Device;
import com.tracking.domain.User;
import com.tracking.utils.SFTSUtil;




public class UserDAO {

    ConnectionManager connectionManager;

    public UserDAO() {
        connectionManager = ConnectionManager.getInstance();
    }

    public User login(User user) throws SQLException {
        Connection conn = connectionManager.getConnection();
        PreparedStatement preparedStatement;
        System.out.println("Connection @ UserDAO "+conn);
        
     
        User currentUser = null;
        try {
            String query = "select * from user where userid= '"+user.getUserName() + "' AND password = '"+user.getPassword()+"' ";
            preparedStatement = conn.prepareStatement(query);
            System.out.println("preparedStatement @ UserDAO "+preparedStatement);
            System.out.println("getUserName @ UserDAO "+user.getUserName());
            System.out.println("getPassword @ UserDAO "+user.getPassword());
          //  preparedStatement.setString(1, user.getUserName());
          //  preparedStatement.setString(2, user.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                currentUser = new User();
                
                //currentUser.setUserId(Integer.parseInt(resultSet.getString("userId")));
                currentUser.setUserName(resultSet.getString("userId"));
                currentUser.setFirstName(resultSet.getString("fName"));
                currentUser.setLastName(resultSet.getString("lName"));
                currentUser.setBirthDate(resultSet.getString("dateofbirth"));
                currentUser.setRole(resultSet.getInt("userrole_roledid"));
                currentUser.setContactNumber(resultSet.getString("contactnumber"));
                currentUser.setUserName(resultSet.getString("userId"));
                currentUser.setCity(resultSet.getString("city"));
                currentUser.setEmail(resultSet.getString("email"));
                currentUser.setGender(resultSet.getString("gender"));
                
                System.out.println("logged in user is  " + currentUser);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null, ex);
                }
            }
        }
        return currentUser;
    	
    }
    public int addUser(User user) throws SQLException{

        Connection conn = connectionManager.getConnection();
        PreparedStatement preparedStatement;
        int result = -1;
        try {
          //  String query = "insert into user (userName, password, firstName, lastName, birthDate, gender, country, userImage) values(?,?,?,?,?,?,?,?)";
          
        	String queryUser = "insert into user ( userid, password, fName, lName, userrole_roledid , " 
        			+ "address1 , address2, street , city , dateofbirth , contactnumber , email  ) "
        			+ " values(?,?,?,?,?,?,?,?,?,?,?,?)";
                        
        	
        	String dobStr  = user.getDateOfBirth();
        	Date dob =  null;
        	if(SFTSUtil.isNotEmpty(dobStr)){
        		try{        			
        			dob = java.sql.Date.valueOf(dobStr);        			
        		}catch(Exception e){
        			dob = null;
        			System.out.println("Exception when converting the date String in to java.sql.Date ");
        		}        		
        	}
        	 	
            preparedStatement = conn.prepareStatement(queryUser);
          
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());            
            preparedStatement.setInt(5, 1);
            preparedStatement.setString(6, user.getAddress1());
            preparedStatement.setString(7, null);
            preparedStatement.setString(8, null);
            preparedStatement.setString(9,null);
            preparedStatement.setDate(10, dob);
            preparedStatement.setString(11,user.getContactNumber() );
            preparedStatement.setString(12, user.getEmail());

            System.out.println("Adding new user in to database ------------- ");
            System.out.println(queryUser);
          
            result = preparedStatement.executeUpdate();
            System.out.println("Add New User successfull  ------------- ");
            if (result > 0) {
                result = 1;
            }
            preparedStatement.close();
            
         
            int deviceDid = -1;
            if ( SFTSUtil.isNotEmpty(user) && SFTSUtil.isNotEmpty(user.getUserDevice()) ){
            	deviceDid = user.getUserDevice().getDeviceDid();
            	if( deviceDid > 0 ){
            	     // call assignDevice
                    assignDevice(user.getUserName(), deviceDid);
            	}
            }
       
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null, ex);
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE , null, ex);
            if(ex instanceof MySQLIntegrityConstraintViolationException){
            	result = -2;
            }
            System.out.println("Add New User failed  ------------- ");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null, ex);
                }
            }
        }
        return result;
    } 
    
    
    public int deleteUser(User user) throws SQLException {

        Connection conn = connectionManager.getConnection();
        PreparedStatement preparedStatement;
        int result = -1;
        try {
          //  String query = "insert into user (userName, password, firstName, lastName, birthDate, gender, country, userImage) values(?,?,?,?,?,?,?,?)";
          
        	String queryUser = "delete from user where userdid= ? ";
        	
        	 	
            preparedStatement = conn.prepareStatement(queryUser);
          
            preparedStatement.setInt(1, user.getUserId());
      

            System.out.println("delete user from database ------------- ");
            System.out.println(queryUser);
          
            result = preparedStatement.executeUpdate();
            System.out.println("delete User successfull  ------------- result ?  "+result);
            if (result > 0) {
                result = 1;
            }
            preparedStatement.close();
            
         
     /*       int deviceDid = -1;
            if ( SFTSUtil.isNotEmpty(user) && SFTSUtil.isNotEmpty(user.getUserDevice()) ){
            	deviceDid = user.getUserDevice().getDeviceDid();
            	if( deviceDid > 0 ){
            	     // call assignDevice
                    assignDevice(user.getUserName(), deviceDid);
            	}
            }*/
       
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null, ex);
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE , null, ex);
            if(ex instanceof MySQLIntegrityConstraintViolationException){
            	result = -2;
            }
            System.out.println("delete User failed  ------------- ");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null, ex);
                }
            }
        }
        return result;
    } 
    
    public User searchUser(User user) throws SQLException{

        Connection conn = connectionManager.getConnection();
        Statement statement = null ;
        ResultSet rs = null;
        int result = -1;
        try {
        	// create normal statement 
        	statement = conn.createStatement();
        	
        	
        	String userName = "";
        	if(SFTSUtil.isNotEmpty(user.getUserName())){
        		// convert the entered value in to uppser case to increase the accuracy of search results 
        		userName = user.getUserName().trim().toUpperCase();
        	}
        
        	
        	String queryUser = "select * from user where UPPER(userid)  = '" + userName +"' "; 
        
      
            // get the result set of the specified user
            rs = statement.executeQuery(queryUser);
        	System.out.println("Search user successful ========== ");
        	result = 1;
        	int userDid = -1;
            while(rs.next()){
            	userDid = rs.getInt("userdid");
            	user.setUserId( userDid);
            	user.setUserId(Integer.parseInt(rs.getString("userDid")));
            	user.setFirstName(rs.getString("fName"));
            	user.setLastName(rs.getString("lName"));
            	user.setAddress1(rs.getString("address1"));
            	user.setStreet(rs.getString("street"));
            	user.setCity(rs.getString("city"));
            	user.setRole(rs.getInt("userrole_roledid"));
            	user.setDateOfBirth(rs.getString("dateofbirth"));
            	user.setContactNumber(rs.getString("contactnumber"));
            	user.setEmail(rs.getString("email"));
            	user.setZipCode(rs.getString("zipcode"));
            }
            
            System.out.println("search user from database ------------- "+user);
          
            
            if (userDid > 1){
            	// populate user's device object
            	int deviceDid = getUserDevice (userDid);
            	if(deviceDid > 0){
            		user.setUserDevice(new Device(deviceDid));
            	}
            }

            statement.close();
         
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null, ex);
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE , null, ex);
            if(ex instanceof MySQLIntegrityConstraintViolationException){
            	result = -2;
            }
            System.out.println("search User failed  ------------- ");
        } finally {
            if (conn != null) {               
               ConnectionManager.close(conn);                
            }
        }
        return user;
    } 
    
    public int updateUser(User user) throws SQLException{

        Connection conn = connectionManager.getConnection();
        PreparedStatement preparedStatement;
        int result = -1;
        try {
         
      /*  	String queryUser = "update user set userid, password, fName, lName, userrole_roledid , " 
        			+ "address1 , address2, street , city , dateofbirth , contactnumber , email   "
        			+ " ?,?,?,?,?,?,?,?,?,?,?,? ";*/
        	
        	
        	String updateUserQuery = "update user set userid = ? , password = ? , fName = ? , lname = ? , address1 = ? , address2 = ? ,"
        			+" street = ? , city = ? , userrole_roledid = ? , dateofbirth = ? , contactnumber = ? , email = ? , zipcode = ?, gender = ? where userdid = ? ";
                        
        	
        	String dobStr  = user.getDateOfBirth();
        	Date dob =  null;
        	if(SFTSUtil.isNotEmpty(dobStr)){
        		try{        			
        			dob = java.sql.Date.valueOf(dobStr);        			
        		}catch(Exception e){
        			dob = null;
        			System.out.println("Exception when converting the date String in to java.sql.Date ");
        		}        		
        	}
        	 	
            preparedStatement = conn.prepareStatement(updateUserQuery);
          
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());            
            preparedStatement.setString(5, user.getAddress1());
            preparedStatement.setString(6, user.getAddress2());
            preparedStatement.setString(7, user.getStreet());
            preparedStatement.setString(8, user.getCity());
            preparedStatement.setInt(9, user.getRole());
            preparedStatement.setDate(10, dob);
            preparedStatement.setString(11,user.getContactNumber() );
            preparedStatement.setString(12, user.getEmail());
            preparedStatement.setString(13, user.getZipCode());
            preparedStatement.setString(14, user.getGender());
            preparedStatement.setInt(15, user.getUserId());
            
            
            // last parameter for the where clause condition to identify PK , uncomment this after finalizing the query
      

            System.out.println("update user in to database ------------- ");
            System.out.println(updateUserQuery);
          
            result = preparedStatement.executeUpdate();
            System.out.println("update User successfull  ------------- result ? "+result);
            if (result > 0) {
                result = 1;
            }
            preparedStatement.close();
            
         
      /*      int deviceDid = -1;
            if ( SFTSUtil.isNotEmpty(user) && SFTSUtil.isNotEmpty(user.getUserDevice()) ){
            	deviceDid = user.getUserDevice().getDeviceDid();
            	if( deviceDid > 0 ){
            	     // call assignDevice
                    assignDevice(user.getUserName(), deviceDid);
            	}
            }*/
       
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null, ex);
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE , null, ex);
            if(ex instanceof MySQLIntegrityConstraintViolationException){
            	result = -2;
            }
            System.out.println("update User failed  ------------- ");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null, ex);
                }
            }
        }
        return result;
    } 
    private void assignDevice(String  userName , int deviceDid){
    	// pass the user name and get user PK , user.userName ()
    	// select userDid from user where userId = userName
    	// insert into user_device   (device_deviceDid , user_userdid )
    	
    	int userDid ;
    	String queryUserDid = "select userDid from user where userId = '"+userName +"'";
    	// result set 
    	// userDid = result.getInt(0);
    	
    	String queryDevice = "insert into user_device   (device_deviceDid , user_userdid ) values (? , ? ) " ;
    	
    	
    }
    
    
    private int getUserDevice(int userDid) throws SQLException{
    	String query = "select device_devicedid from user_device where user_userdid = ? ";
    	PreparedStatement preparedStatement;
    	int deviceDid = -1;
    	Connection conn = connectionManager.getConnection();
    	try {
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, userDid);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()){
				deviceDid = rs.getInt(0);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
          
    	return deviceDid;
    }
    
    
    public ArrayList<User> getAvailableUsers() throws SQLException{
		Connection conn = connectionManager.getConnection();
		
		ArrayList<User> users =  new ArrayList<User>();
		
	
		System.out.println("Connection @ UserDAO " + conn);
		User user = null;
		String query = "select * from user ";
		CallableStatement callable = conn.prepareCall(query);
		boolean results = callable.execute();
		int userDid = -1;
		// Loop through the available result sets.
		while (results) {
			ResultSet rs = callable.getResultSet();
			System.out.println("ResultSet @ load available users "+users);
			
			try{
				// Retrieve data from the result set.
				while (rs.next()) {
					user = new User();
					userDid = rs.getInt("userdid");
	            	user.setUserId( userDid);
	            	user.setUserId(Integer.parseInt(rs.getString("userDid")));
	            	user.setFirstName(rs.getString("fName"));
	            	user.setLastName(rs.getString("lName"));
	            	user.setAddress1(rs.getString("address1"));
	            	user.setStreet(rs.getString("street"));
	            	user.setCity(rs.getString("city"));
	            	user.setRole(rs.getInt("userrole_roledid"));
	            	user.setDateOfBirth(rs.getString("dateofbirth"));
	            	user.setContactNumber(rs.getString("contactnumber"));
	            	user.setEmail(rs.getString("email"));
	            	user.setZipCode(rs.getString("zipcode"));
	            	
	            	System.out.println("user @ load avilable users "+user);
	            	users.add(user);
	            	user = null ;
				}
				rs.close();
			}catch(Exception ex){
				System.out.println("Exception @ load all users");
			}
			
			// Check for next result set
			results = callable.getMoreResults();
		}

		return users;
    }
    
    
    
    
    
    
}