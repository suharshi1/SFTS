package com.SFTS.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.SFTS.Base.Entities.*;


public class UserDAO {

    ConnectionManager connectionManager;

    public UserDAO() {
        connectionManager = ConnectionManager.getInstance();
    }

    public User login(User user) {
        Connection conn = connectionManager.getConnection();
        PreparedStatement preparedStatement;
        System.out.println("Connection @ UserDAO "+conn);
        User userdao = null;
        try {
            String query = "select * from user where userName=? && password=?";
            preparedStatement = conn.prepareStatement(query);
            System.out.println("preparedStatement @ UserDAO "+preparedStatement);
            System.out.println("getUserName @ UserDAO "+user.getUserName());
            System.out.println("getPassword @ UserDAO "+user.getPassword());
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userdao = new User();
                userdao.setUserId(resultSet.getInt("userId"));
                userdao.setUserName(resultSet.getString("userName"));
                userdao.setFirstname(resultSet.getString("firstName"));
                userdao.setLastname(resultSet.getString("lastName"));
                userdao.setBirthdate(resultSet.getString("birthDate"));
                userdao.setGender(resultSet.getString("gender"));
                userdao.setCountry(resultSet.getString("country"));
                userdao.setUserImage(resultSet.getString("userImage"));
                System.out.println("pasindu country" + userdao.getCountry());
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
        return userdao;
    }
    public int addUser(User user) {

        Connection conn = connectionManager.getConnection();
        PreparedStatement preparedStatement;
        int result = -1;
        try {
          //  String query = "insert into user (userName, password, firstName, lastName, birthDate, gender, country, userImage) values(?,?,?,?,?,?,?,?)";
          
        	String query = "insert into user (userName, password, firstName, lastName  ) values(?,?,?,? )";
                        
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getLastname());
           /* preparedStatement.setString(5, user.getBirthdate());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.setString(7, user.getCountry());
            preparedStatement.setString(8, user.getUserImage());*/
            System.out.println(query);
            result = preparedStatement.executeUpdate();

            if (result > 0) {
                result = 1;
            }
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
        return result;
    } 
    
}