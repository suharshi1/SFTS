package com.tracking.utils;

public enum UserRole {

	ADMIN(1, "Admin"), SALESEXECUTIVE(2,"SalesExecutive");

	
	private int roleDid;
	
	private String role;
	
	UserRole(int roleDid , String role){
		this.roleDid = roleDid;
		this.role = role;
	};
	
	public int getRoleDid(){
		return roleDid;
	}
	
	public String getRole(){
		return role;
	}
	
}
