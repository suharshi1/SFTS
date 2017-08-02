package com.tracking.connectivity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.tracking.domain.DeviceDTO;
import com.tracking.domain.UserDTO;
import com.tracking.utils.SFTSUtil;

public class DeviceDAO {

	ConnectionManager connectionManager;

	public DeviceDAO() {
		connectionManager = ConnectionManager.getInstance();
	}

	public UserDTO login(UserDTO user) throws SQLException {
		Connection conn = connectionManager.getConnection();
		PreparedStatement preparedStatement;
		System.out.println("Connection @ UserDAO " + conn);

		UserDTO currentUser = null;
		try {
			String query = "select * from user where userid= '"
					+ user.getUserName() + "' AND password = '"
					+ user.getPassword() + "' ";
			preparedStatement = conn.prepareStatement(query);
			System.out.println("preparedStatement @ UserDAO "
					+ preparedStatement);
			System.out.println("getUserName @ UserDAO " + user.getUserName());
			System.out.println("getPassword @ UserDAO " + user.getPassword());
			// preparedStatement.setString(1, user.getUserName());
			// preparedStatement.setString(2, user.getPassword());

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				currentUser = new UserDTO();
				// currentUser.setUserId(Integer.parseInt(resultSet.getString("userId")));
				currentUser.setUserName(resultSet.getString("userId"));
				currentUser.setFirstName(resultSet.getString("fName"));
				currentUser.setLastName(resultSet.getString("lName"));
				currentUser.setBirthDate(resultSet.getString("dateofbirth"));

				System.out.println(" currentUser " + currentUser);
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException ex) {
			Logger.getLogger(UserDAO.class.getName()).log(Level.WARNING, null,
					ex);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					Logger.getLogger(UserDAO.class.getName()).log(
							Level.WARNING, null, ex);
				}
			}
		}
		return currentUser;

	}

	public int addDevice(DeviceDTO device) throws SQLException {

		Connection conn = connectionManager.getConnection();
		PreparedStatement preparedStatement;
		int result = -1;
		try {
			String queryDevice = "insert into sfts.device ( deviceId, description, IMEI)"
					+ "values(?,?,?)";

			preparedStatement = conn.prepareStatement(queryDevice);

			preparedStatement.setString(1, device.getDeviceId());
			preparedStatement.setString(2, device.getDescription());
			preparedStatement.setString(3, device.getImei());

			System.out
					.println("Adding new device in to database ------------- ");
			System.out.println(queryDevice);

			result = preparedStatement.executeUpdate();
			System.out.println("Add New device successfull  ------------- "
					+ result);
			if (result > 0) {
				result = 1;
			}
			preparedStatement.close();
		} catch (SQLException ex) {
			Logger.getLogger(DeviceDAO.class.getName()).log(Level.WARNING,
					null, ex);
			Logger.getLogger(DeviceDAO.class.getName()).log(Level.SEVERE, null,
					ex);
			if (ex instanceof MySQLIntegrityConstraintViolationException) {
				result = -2;
			}
			System.out.println("Add new device failed  ------------- ");
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					Logger.getLogger(UserDAO.class.getName()).log(
							Level.WARNING, null, ex);
				}
			}
		}
		return result;
	}

	public int deleteDevice(DeviceDTO device) throws SQLException {

		Connection conn = connectionManager.getConnection();
		PreparedStatement preparedStatement;
		int result = -1;
		try {
			String queryDevice = "delete from device where devicedid =?";

			preparedStatement = conn.prepareStatement(queryDevice);

			
			preparedStatement.setInt(1, device.getDeviceDid());

			System.out.println("delete device from database ------------- ");
			System.out.println(queryDevice);

			result = preparedStatement.executeUpdate();
			
			preparedStatement.close();

		} catch (SQLException ex) {
			Logger.getLogger(DeviceDAO.class.getName()).log(Level.WARNING,
					null, ex);
			Logger.getLogger(DeviceDAO.class.getName()).log(Level.SEVERE, null,
					ex);
			if (ex instanceof MySQLIntegrityConstraintViolationException) {
				result = -2;
			}
			System.out.println("delete Device failed  ------------- ");
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					Logger.getLogger(UserDAO.class.getName()).log(
							Level.WARNING, null, ex);
				}
			}
		}
		return result;
	}

	public DeviceDTO searchDevice(DeviceDTO device) throws SQLException {

		Connection conn = connectionManager.getConnection();
		Statement statement = null;
		ResultSet rs = null;
		int result = -1;
		try {
			// create normal statement
			statement = conn.createStatement();
			String deviceId = device.getDeviceId();
			String queryDevice = "select * from device where deviceId = '"+deviceId+"'";
			rs = statement.executeQuery(queryDevice);
			System.out.println("Search user successful ========== queryUser "+queryDevice);
			result = 1;
			int devicedid = -1;
			while (rs.next()) {
				devicedid = rs.getInt("devicedid");
				device.setDeviceDid(devicedid);
				device.setDeviceDid(Integer.parseInt(rs.getString("deviceDid")));
				device.setDeviceId(rs.getString("deviceId"));
				device.setDescription(rs.getString("description"));
				device.setImei(rs.getString("imei"));

			}
			System.out.println("search device from database ------------- "
					+ device);
			statement.close();
		} catch (SQLException ex) {
			Logger.getLogger(DeviceDAO.class.getName()).log(Level.WARNING,
					null, ex);
			Logger.getLogger(DeviceDAO.class.getName()).log(Level.SEVERE, null,
					ex);
			if (ex instanceof MySQLIntegrityConstraintViolationException) {
				result = -2;
			}
			System.out.println("search device failed  ------------- ");
		} finally {
			if (conn != null) {
				ConnectionManager.close(conn);
			}
		}
		return device;
	}

	public int updateDevice(DeviceDTO device) throws SQLException {

		Connection conn = connectionManager.getConnection();
		PreparedStatement preparedStatement = null ;
		int result = -1;
		try {
			// String query =
			// "insert into user (userName, password, firstName, lastName, birthDate, gender, country, userImage) values(?,?,?,?,?,?,?,?)";

			String queryDevice = "update device set deviceId = ? , description = ? , IMEI = ?   where devicedid = ? ";

			preparedStatement = conn.prepareStatement(queryDevice);

			preparedStatement.setString(1, device.getDeviceId());
			preparedStatement.setString(2, device.getDescription());
			preparedStatement.setString(3, device.getImei());
			preparedStatement.setInt(4, device.getDeviceDid());

			System.out.println("update device in to database ------------- ");
			System.out.println(queryDevice);

			result = preparedStatement.executeUpdate();
			System.out.println("update device successfull  ------------- ");
			if (result > 0) {
				result = 1;
			}
			preparedStatement.close();

		} catch (SQLException ex) {
			Logger.getLogger(DeviceDAO.class.getName()).log(Level.WARNING,
					null, ex);
			Logger.getLogger(DeviceDAO.class.getName()).log(Level.SEVERE, null,
					ex);
			if (ex instanceof MySQLIntegrityConstraintViolationException) {
				result = -2;
			}
			System.out.println("update device failed  ------------- ");
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					Logger.getLogger(DeviceDAO.class.getName()).log(
							Level.WARNING, null, ex);
				}
			}
			
			
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					Logger.getLogger(DeviceDAO.class.getName()).log(
							Level.WARNING, null, ex);
				}
			}
		}
		return result;
	}
	

    public ArrayList<DeviceDTO> getAvailableDevices()throws SQLException{
		Connection conn = connectionManager.getConnection();

		ArrayList<DeviceDTO> devices = new ArrayList<DeviceDTO>();

		System.out.println("Connection @ UserDAO.getAvailableDevices " + conn);
		DeviceDTO device = null;
		String query = "select * from device ";
		CallableStatement callable = conn.prepareCall(query);
		boolean results = callable.execute();

		ResultSet rs = null;
		try {
			// Loop through the available result sets.
			while (results) {
				rs = callable.getResultSet();
				System.out.println("ResultSet @ load available devices " + devices);

				// Retrieve data from the result set.
				while (rs != null && rs.next()) {
					device = new DeviceDTO();
					device.setDescription(rs.getString("description"));
					device.setDeviceId(rs.getString("deviceId"));
					device.setImei(rs.getString("imei"));
					device.setDeviceDid(Integer.valueOf(rs.getString("deviceDid")));

					System.out.println("devices @ load avilable devices " + device);
					devices.add(device);
					device = null;
				}

				// Check for next result set
				results = callable.getMoreResults();
			}
		} catch (Exception ex) {
			System.out.println("Exception @ load all users ");
			ex.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (callable != null) {
				callable.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		return devices;
    }
    
	
	
	
}
