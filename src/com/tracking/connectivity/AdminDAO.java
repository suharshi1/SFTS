package com.tracking.connectivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tracking.domain.RoleDTO;

public class AdminDAO {

	ConnectionManager connectionManager;

	public AdminDAO() {
		connectionManager = ConnectionManager.getInstance();
	}
	
	public List<RoleDTO> getUserRoles() throws SQLException{
		
		Connection conn = connectionManager.getConnection();
		String query = "Select * from userrole ";
		System.out.println("Connection @ UserDAO " + conn);
		List<RoleDTO> roleList = new ArrayList<RoleDTO> ();
		ResultSet rst =null;
		PreparedStatement stmnt = conn.prepareStatement(query);
		try{
			RoleDTO role ;
			rst = stmnt.executeQuery();
			while(rst.next()){
				role  = new RoleDTO();
				role.setRoleDid(Integer.valueOf(rst.getString("roleDid")));
				role.setRoleId(rst.getString("description"));
				roleList.add(role);				
			}		
			
		}catch(SQLException sqlex){
			Logger.getLogger(AdminDAO.class.getName()).log(Level.WARNING, null, sqlex);
		}finally {
			if(rst != null ){
		//		rst.close();
			}
			if(stmnt != null){
		//		stmnt.close();
			}
			if(conn != null ){
				ConnectionManager.close(conn);
			}
		}
		
		
		return roleList;
	}
	
}
