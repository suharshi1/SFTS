package com.tracking.connectivity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Statement;
import java.text.ParseException;
import java.sql.CallableStatement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.tracking.admin.entity.Device;
import com.tracking.domain.DeviceDTO;
import com.tracking.domain.UserDTO;
import com.tracking.utils.PasswordUtil;
import com.tracking.utils.SFTSUtil;

public class UserDAO {

	ConnectionManager connectionManager;

	public UserDAO() {
		connectionManager = ConnectionManager.getInstance();
	}

	public UserDTO login(UserDTO user) throws SQLException {
		Connection conn = connectionManager.getConnection();
		PreparedStatement preparedStatement;
		System.out.println("Connection @ UserDAO " + conn);

		UserDTO currentUser = null;
		try {

			String encryptedPassword = PasswordUtil.encrypt(user.getPassword());
			
			String query = "select * from user where userid= '" + user.getUserName() + "' AND password = '"
					+ encryptedPassword + "' ";
			preparedStatement = conn.prepareStatement(query);
			System.out.println("preparedStatement @ UserDAO " + preparedStatement);
			System.out.println("getUserName @ UserDAO " + user.getUserName());
			System.out.println("getPassword @ UserDAO " + user.getPassword()+ " encrypted password is "+encryptedPassword);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				currentUser = new UserDTO();
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
		} catch (Exception e) {
			e.printStackTrace();
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

	public int addUser(UserDTO user) throws SQLException {

		Connection conn = connectionManager.getConnection();
		PreparedStatement preparedStatement;
		int result = -1;

		boolean isEncrypted = false;
		String encryptedPassword = null;
		try {
			encryptedPassword = PasswordUtil.encrypt(user.getPassword());
			isEncrypted = true;
			System.out.println("Password encryption is successful, saving the new user data");
		} catch (Exception e) {
			System.err.println("Error while encrypting password");
			isEncrypted = false;
		}

		if (isEncrypted) {
			try {

				String queryUser = "insert into user ( userid, password, fName, lName, userrole_roledid , "
						+ "address1 , address2, street , city , dateofbirth , contactnumber , email  ) "
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?)";

				String dobStr = user.getDateOfBirth();
				Date dob = null;
			
				if (SFTSUtil.isNotEmpty(dobStr)) {
					try {
						dob = SFTSUtil.getBirthDate(dobStr);
					} catch (ParseException e1) {
						dob = null;
						System.err.println("ParseException when converting the date String in to java.sql.Date ");
						
					} catch (Exception e) {
						dob = null;
						System.err.println("Exception when converting the date String in to java.sql.Date ");
						
					}	
				}
				
			

				preparedStatement = conn.prepareStatement(queryUser);

				preparedStatement.setString(1, user.getUserName());
				preparedStatement.setString(2, encryptedPassword);
				preparedStatement.setString(3, user.getFirstName());
				preparedStatement.setString(4, user.getLastName());
				preparedStatement.setInt(5, 1);
				preparedStatement.setString(6, user.getAddress1());
				preparedStatement.setString(7, user.getStreet());
				preparedStatement.setString(8, user.getCity());
				preparedStatement.setString(9, null);
				preparedStatement.setDate(10, dob);
				preparedStatement.setString(11, user.getContactNumber());
				preparedStatement.setString(12, user.getEmail());

				System.out.println("Adding new user in to database ------------- ");
				System.out.println(queryUser);

				result = preparedStatement.executeUpdate();
				System.out.println("Add New User successfull  ------------- ");
				if (result > 0) {
					result = 1;
				}
				UserDTO newUser = searchUser(user);
				preparedStatement.close();

				int deviceDid = -1;
				if (SFTSUtil.isNotEmpty(user) && SFTSUtil.isNotEmpty(user.getUserDevice())) {
					deviceDid = user.getUserDevice().getDeviceDid();
					if (deviceDid > 0) {
						// call assignDevice
						assignDevice(user.getUserName(), deviceDid);
					}
				}

			} catch (SQLException ex) {
				Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null, ex);
				Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
				if (ex instanceof MySQLIntegrityConstraintViolationException) {
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
		
		return result;
	}

	public int deleteUser(UserDTO user) throws SQLException {

		Connection conn = connectionManager.getConnection();
		PreparedStatement preparedStatement;
		int result = -1;
		try {
			// String query = "insert into user (userName, password, firstName, lastName,
			// birthDate, gender, country, userImage) values(?,?,?,?,?,?,?,?)";

			String queryUser = "update user set isactive = 0  where userdid= ? ";

			preparedStatement = conn.prepareStatement(queryUser);

			preparedStatement.setInt(1, user.getUserId());

			System.out.println("delete user from database ------------- ");
			System.out.println(queryUser);

			result = preparedStatement.executeUpdate();
			System.out.println("delete User successfull  ------------- result ?  " + result);

			preparedStatement.close();


		} catch (SQLException ex) {
			Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null, ex);
			Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
			if (ex instanceof MySQLIntegrityConstraintViolationException) {
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

	public UserDTO searchUser(UserDTO user) throws SQLException {

		Connection conn = connectionManager.getConnection();
		Statement statement = null;
		ResultSet rs = null;
		int result = -1;
		try {
			// create normal statement
			statement = conn.createStatement();

			String userName = "";
			if (SFTSUtil.isNotEmpty(user.getUserName())) {
				// convert the entered value in to uppser case to increase the accuracy of
				// search results
				userName = user.getUserName().toUpperCase();
			}

			String queryUser = "select u.* , ud.device_devicedid as userdevice from user u , user_device ud where upper(userid)  like '%" + userName + "%' and isactive =1";

			// get the result set of the specified user
			rs = statement.executeQuery(queryUser);
			System.out.println("Search user successful ========== ");
			result = 1;
			int userDid = -1;
			int deviceDid = -1;
			while (rs.next()) {
				userDid = rs.getInt("userDid");
				deviceDid = rs.getInt("userdevice");
				user.setUserId(userDid);
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
				user.setUserName(rs.getString("userid"));
			}

			System.out.println("search user from database ------------- " + user.getDateOfBirth());
			System.out.println("role " + user.getRole());
			

			if (userDid > 0 && deviceDid > 0) {
				// populate user's device object
				//int deviceDid = getUserDevice(userDid);
				if (deviceDid > 0) {
					user.setUserDevice(getUserDevice(deviceDid));
				}
			}
			
			System.out.println("device user "+user.getUserDevice());

			statement.close();

		} catch (SQLException ex) {
			Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null, ex);
			Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
			if (ex instanceof MySQLIntegrityConstraintViolationException) {
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

	public int updateUser(UserDTO user) throws SQLException {

		Connection conn = connectionManager.getConnection();
		PreparedStatement preparedStatement;
		int result = -1;
		try {


			String updateUserQuery = "update user set userid = ? , password = ? , fName = ? , lname = ? , address1 = ? , address2 = ? ,"
					+ " street = ? , city = ? , userrole_roledid = ? , dateofbirth = ? , contactnumber = ? , email = ? , zipcode = ?, gender = ? where userid = ? ";

			String dobStr = user.getDateOfBirth();
			Date dob = null;
			
			if (SFTSUtil.isNotEmpty(dobStr)) {
				try {
					dob = SFTSUtil.getBirthDate(dobStr);
				} catch (ParseException e1) {
					dob = null;
					System.err.println("ParseException when converting the date String in to java.sql.Date ");
					
				} catch (Exception e) {
					dob = null;
					System.err.println("Exception when converting the date String in to java.sql.Date ");
					
				}	
			}

			preparedStatement = conn.prepareStatement(updateUserQuery);

			boolean isEncrypted = false;
			String encryptedPassword = null;
			try {
				encryptedPassword = PasswordUtil.encrypt(user.getPassword());
				isEncrypted = true;
				System.out.println("Password encryption is successful, saving the new user data");
			} catch (Exception e) {
				System.err.println("Error while encrypting password");
				isEncrypted = false;
			}
			
			if(isEncrypted) {
				preparedStatement.setString(1, user.getUserName());
				preparedStatement.setString(2, encryptedPassword);
				preparedStatement.setString(3, user.getFirstName());
				preparedStatement.setString(4, user.getLastName());
				preparedStatement.setString(5, user.getAddress1());
				preparedStatement.setString(6, user.getAddress2());
				preparedStatement.setString(7, user.getStreet());
				preparedStatement.setString(8, user.getCity());
				preparedStatement.setInt(9, user.getRole());
				preparedStatement.setDate(10, dob);
				preparedStatement.setString(11, user.getContactNumber());
				preparedStatement.setString(12, user.getEmail());
				preparedStatement.setString(13, user.getZipCode());
				preparedStatement.setString(14, user.getGender());
				preparedStatement.setString(15, user.getUserName());

				System.out.println("update user in to database ------------- ");
				System.out.println(updateUserQuery);

				result = preparedStatement.executeUpdate();
				System.out.println("update User successfull  ------------- result ? " + result);
				if (result > 0) {
					result = 1;
				}
				
			}else {
				result = -3;
				System.err.println("error occured while decrypting the password, user was not updated");
			}
			
			preparedStatement.close();

			// last parameter for the where clause condition to identify PK , uncomment this
			// after finalizing the query


		} catch (SQLException ex) {
			Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null, ex);
			Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
			if (ex instanceof MySQLIntegrityConstraintViolationException) {
				result = -2;
			}
			System.out.println("update User failed  ------------- ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	private void assignDevice(String userId, int deviceDid) {
		// pass the user name and get user PK , user.userName ()
		// select userDid from user where userId = userName
		// insert into user_device (device_deviceDid , user_userdid )

		int userDid;
		String queryUserDid = "select userDid from user where upper(userId) = '" + userId + "'";
		// result set
		// userDid = result.getInt(0);

		String queryDevice = "insert into user_device   (device_deviceDid , user_userdid ) values (? , ? ) ";
		
		PreparedStatement preparedStatement;
		try {
			Connection conn = connectionManager.getConnection();
			preparedStatement = conn.prepareStatement(queryDevice);
			preparedStatement.setInt(1, deviceDid);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				deviceDid = rs.getInt(0);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}

	/*
	private int getUserDevice(int userDid) throws SQLException {
		String query = "select device_devicedid from user_device where user_userdid = ? ";
		PreparedStatement preparedStatement;
		int deviceDid = -1;
		Connection conn = connectionManager.getConnection();
		try {
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, userDid);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				deviceDid = rs.getInt(0);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return deviceDid;
	}
	*/

	
	private DeviceDTO getUserDevice(int deviceDid) throws SQLException {
		String query = "select devicedid , deviceid , description , imei from device where devicedid = ? ";
		PreparedStatement preparedStatement;
		DeviceDTO deviceDto = null;
		Connection conn = connectionManager.getConnection();
		try {
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, deviceDid);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				deviceDto = new DeviceDTO();
				deviceDto.setDeviceDid(rs.getInt("devicedid"));
				deviceDto.setDeviceId(rs.getString("deviceid"));
				deviceDto.setDescription(rs.getString("description"));
				deviceDto.setImei(rs.getString("imei"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return deviceDto;
	}
	
	public ArrayList<UserDTO> getAvailableUsers() throws SQLException {
		Connection conn = connectionManager.getConnection();

		ArrayList<UserDTO> users = new ArrayList<UserDTO>();

		System.out.println("Connection @ UserDAO " + conn);
		UserDTO user = null;
		String query = "select * from user where isactive = 1";
		CallableStatement callable = conn.prepareCall(query);
		boolean results = callable.execute();
		int userDid = -1;
		// Loop through the available result sets.
		while (results) {
			ResultSet rs = callable.getResultSet();
			System.out.println("ResultSet @ load available users " + users);

			try {
				// Retrieve data from the result set.
				while (rs.next()) {
					user = new UserDTO();
					userDid = rs.getInt("userdid");
					user.setUserId(userDid);
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

					System.out.println("user @ load avilable users " + user);
					users.add(user);
					user = null;
				}
				rs.close();
			} catch (Exception ex) {
				System.out.println("Exception @ load all users");
			}

			// Check for next result set
			results = callable.getMoreResults();
		}

		return users;
	}

	
	
	public static void main(String[] args) throws Exception {

		ConnectionManager manager = ConnectionManager.getInstance();
		manager.getConnection();
		

	}


}