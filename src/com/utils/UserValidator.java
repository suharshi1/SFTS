package com.utils;



import com.SFTS.Base.Entities.User;

public class UserValidator {

	
	public static boolean isValidUser(User user){
		boolean isVaild = false;
	// TODO Suwimali add the full validation chain to this 	
	//	fName == null ||lName == null || address==null || dob==null || role==null || contactNo==null || email==null 
		
		if(user.getFirstname() != null && user.getLastname() != null  ){
		
			isVaild = true;		
		
		}
		
		return isVaild;	
		
	}	
	
	
	
}
