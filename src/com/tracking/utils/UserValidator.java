package com.tracking.utils;

import com.tracking.domain.UserDTO;

public class UserValidator {

	
	public static boolean isValidUser(UserDTO user){
		boolean isVaild = false;
	// TODO Suwimali add the full validation chain to this 	
	//	fName == null ||lName == null || address==null || dob==null || role==null || contactNo==null || email==null 
		
		if(user.getFirstName() != null && user.getLastName() != null  ){
		
			isVaild = true;		
		
		}
		
		return isVaild;	
		
	}	
	
	
	
}
