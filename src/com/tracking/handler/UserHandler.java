package com.tracking.handler;

import java.sql.SQLException;

import com.tracking.domain.User;
import com.tracking.connectivity.UserDAO;

public class UserHandler {

	
	public int addUser(User user){
		int add =-1;
		try {
			add = new UserDAO().addUser(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return add;
	}
	
	
	public void editUser(){
		
	}
	
	
	public void deleteUser(){
		
	}
	
}
