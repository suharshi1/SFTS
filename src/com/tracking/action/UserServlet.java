/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracking.action;

import com.tracking.domain.Device;
import com.tracking.domain.User;
import com.tracking.connectivity.DeviceDAO;
import com.tracking.connectivity.UserDAO;
import com.tracking.token.Token.*;
import com.tracking.utils.Constants;
import com.tracking.utils.SFTSUtil;
import com.tracking.utils.UserValidator;

import java.io.IOException;
import java.io.PrintWriter;

import com.tracking.token.*;

import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Token token = null;
		UserDAO userDAO = new UserDAO();
		System.out.println("Process request @ User servlet ");

		String userCommand = request.getParameter("user_command");

		System.out.println("user command in UserServlet is " + userCommand);

		if (Constants.COMMAND_SEARCH_USER.equals(userCommand)) { // Search Functionality
			// update existing user
			String userName = request.getParameter("searchName");
			if (SFTSUtil.isNotEmpty(userName)) {

				System.out.println(" UserServlet user search ");
				User user = new User();
				user.setUserName(userName);
				User userSearch = null;
				try {
					userSearch = new UserDAO().searchUser(user);
				} catch (SQLException e) {
					System.out.println("exception in search user ");
				}
				request.getSession().setAttribute("searchedUser", userSearch);
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				httpServletResponse.sendRedirect(request.getContextPath()
						+ "/updateUser.jsp");
			}
		} else { // Add , Update , Delete
			String fName = request.getParameter("inputFName");
			String lName = request.getParameter("inputLName");
			String address1 = request.getParameter("inputAddress");
			String address2 = request.getParameter("inputAddress2");
			String street = request.getParameter("inputStreet");
			String city = request.getParameter("inputCity");

			String dob = request.getParameter("inputDoB");
			String role = request.getParameter("inputRole");
			String contactNo = request.getParameter("inputContactNo");
			String email = request.getParameter("inputEmail");
			String password = request.getParameter("password");
			String userName = request.getParameter("userName");
			// String role = request.getParameter("roleUser");
			int deviceDid = -1;

			String deviceDidStr = request.getParameter("deviceDid");
			if (SFTSUtil.isNotEmpty(deviceDidStr)) {
				try {
					deviceDid = Integer.parseInt(deviceDidStr);
				} catch (Exception e) {
					deviceDid = -1;
				}
			}
			// receive the select elements value

			Device device = null;
			if (deviceDid > 0) {
				device = new Device();
				device.setDeviceDid(deviceDid);
			}

			User user = new User();
			user.setFirstName(fName);
			user.setLastName(lName);
			user.setAddress1(address1);
			user.setAddress2(address2);
			Date date1 = null;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			try {
				date1 = df.parse(dob);
			} catch (ParseException e1) {
				date1 = null;
			} catch (Exception e) {
				date1 = null;
			}
			user.setDateOfBirth(dob);
			user.setPassword(password);
			user.setEmail(email);
			user.setUserName(userName);
			user.setCity(city);
			user.setStreet(street);
			user.setContactNumber(contactNo);

			// assign the device to the user
			user.setUserDevice(device);

			try {
				if (SFTSUtil.isNotEmpty(role)) {
					user.setRole(Integer.valueOf(role));
				}

			} catch (Exception e) {
				user.setRole(1);
				System.out.println("User role null");
			}

			if (Constants.COMMAND_ADD_USER.equals(userCommand)) {

				request.getSession().setAttribute("currentUser", user);

				System.out.println("populated user information " + user);

				if (!UserValidator.isValidUser(user)) {
					System.out.println(" UserServlet user invalid ");
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse.sendRedirect(request.getContextPath()
							+ "/addUser.jsp");
				} else {
					try {
						System.out.println(" UserServlet user valid ");
						// save the user
						int add = new UserDAO().addUser(user);
						System.out.println("add successful ? --------------   "
								+ add);
						System.out.println("");
						if (add == 1) {
							// user added successfully
							HttpServletResponse httpServletResponse = (HttpServletResponse) response;
							request.getSession().setAttribute("statusmsg",
									"User creation success !");
							httpServletResponse.sendRedirect(request
									.getContextPath() + "/addUser.jsp");
						} else if (add == -2) { // username already existing
							request.getSession().setAttribute("statusmsg",
									"User Name Already existing !");
						} else {
							request.getSession().setAttribute("statusmsg",
									"User creation faliure !");
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (Constants.COMMAND_UPDATE_USER.equals(userCommand)) {
				// update existing user
				String existingDid = request.getParameter("userId"); // existing userdid PK
				if(SFTSUtil.isNotEmpty(existingDid)){
					user.setUserId(Integer.parseInt(existingDid));
				}
			
				System.out.println("populated user information " + user);
				if (!UserValidator.isValidUser(user)) {
					System.out.println(" UserServlet user invalid ");
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse.sendRedirect(request.getContextPath()+ "/updateUser.jsp");
				} else {
					try {
						System.out.println(" UserServlet user valid ");
						// update the user
						int update = new UserDAO().updateUser(user);
						System.out.println("update successful ? --------------   "
								+ update);
						System.out.println("");
						if (update == 1) {
							// user added successfully
							user = userDAO.searchUser(user);
							HttpServletResponse httpServletResponse = (HttpServletResponse) response;
							request.getSession().setAttribute("statusmsg","Update success !");
							request.getSession().setAttribute("searchedUser", user);
							httpServletResponse.sendRedirect(request.getContextPath() + "/updateUser.jsp");
						} else {
							request.getSession().setAttribute("statusmsg","user update faliure !");
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} else if (Constants.COMMAND_DELETE_USER.equals(userCommand)) {
				// update existing user
				String existingDid = request.getParameter("userId"); // existing userdid PK
				if(SFTSUtil.isNotEmpty(existingDid)){
					user.setUserId(Integer.parseInt(existingDid));
				}
				System.out.println("populated user information " + user);
				if (!UserValidator.isValidUser(user)) {
					System.out.println(" UserServlet user invalid ");
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse.sendRedirect(request.getContextPath()
							+ "/deleteUser.jsp");
				} else {
					try {
						System.out.println(" UserServlet user valid ");
						// update the user
						int delete = new UserDAO().deleteUser(user);
						System.out.println("add successful ? --------------   "+ delete);
						
						if (delete == 1) {
						
							HttpServletResponse httpServletResponse = (HttpServletResponse) response;
							request.getSession().setAttribute("statusmsg","User delete success !");
							httpServletResponse.sendRedirect(request.getContextPath() + "/deleteUser.jsp");
						
						} else {
							request.getSession().setAttribute("statusmsg","User delete faliure !");
						}

					} catch (Exception e) {
						System.out.println("Exception "+e.getMessage());
					}
				}
			}
		}

	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
