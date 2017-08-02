package com.tracking.action;

import com.tracking.domain.DeviceDTO;
import com.tracking.domain.UserDTO;
import com.tracking.connectivity.DeviceDAO;
import com.tracking.connectivity.UserDAO;
import com.tracking.token.Token.*;
import com.tracking.utils.Constants;
import com.tracking.utils.DeviceValidator;
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

public class DeviceServlet extends HttpServlet {

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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Token token = null;

		// initialize the device status message to blank at the beginning
		request.getSession().setAttribute("statusmsg","");
		
		DeviceDAO deviceDAO = new DeviceDAO();
		System.out.println("Process request @ User servlet ");

		String userCommand = request.getParameter("user_command");

		System.out.println("user command in DeviceServlet is " + userCommand);

		if (Constants.COMMAND_SEARCH_DEVICE.equals(userCommand)) {
			String deviceId = request.getParameter("searchDevice");
			String fromPage = request.getParameter("fromPage");
			if (SFTSUtil.isNotEmpty(deviceId)) {
				System.out.println(" DeviceServlet device search ");
				DeviceDTO device = new DeviceDTO();
				device.setDeviceId(deviceId);
				DeviceDTO deviceSearch = null;
				try {
					deviceSearch = new DeviceDAO().searchDevice(device);
				} catch (SQLException e) {
					System.out.println("exception in search device ");
				}
				request.getSession().setAttribute("searchedDevice", deviceSearch);
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				if (SFTSUtil.isNotEmpty(fromPage)&& fromPage.equals("deletePage"))  {
					httpServletResponse.sendRedirect(request.getContextPath()+ "/deleteDevice.jsp");
				}else
				httpServletResponse.sendRedirect(request.getContextPath()+ "/updateDevice.jsp");
				
			}
		} else {

			if (Constants.COMMAND_ADD_DEVICE.equals(userCommand)) {
				System.out.println("device command in DeviceServlet is "+ userCommand);

				DeviceDTO device = getDevide(request);

				if (!DeviceValidator.isValidDevice(device)) {
					System.out.println(" DeviceValidator device invalid ");
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse.sendRedirect(request.getContextPath()+ "/addDevice.jsp");
				} else {
					try {
						System.out.println(" DeviceServlet device valid ");
						// save the device
						int add = deviceDAO.addDevice(device);
						System.out.println("add successful ? --------------   "	+ add);
					
						if (add == 1) {
							// user added successfully
							device = deviceDAO.searchDevice(device);
							HttpServletResponse httpServletResponse = (HttpServletResponse) response;
							request.getSession().setAttribute("statusmsg","Device creation successsull !");
							request.getSession().setAttribute("searchedDevice", device);
							httpServletResponse.sendRedirect(request.getContextPath() + "/addDevice.jsp");
						} else if (add == -2) {
							request.getSession().setAttribute("statusmsg",	"device Name Already existing !");
						} else {
							request.getSession().setAttribute("statusmsg","User creation faliure !");
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (Constants.COMMAND_UPDATE_DEVICE.equals(userCommand)) {
				DeviceDTO device = getDevide(request);

				System.out.println("populated user information @ update " + device);
				if (!DeviceValidator.isValidDevice(device)) {
					System.out.println(" DeviceServlet user invalid ");
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse.sendRedirect(request.getContextPath()+ "/updateDevice.jsp");
				} else {
					try {
						System.out.println(" DeviceServlet user valid ");
						// delete the user
						int update = deviceDAO.updateDevice(device);
						System.out.println("update successful ? --------------   "+ update);

						if (update == 1) {
							// device  updated successfully
							device = deviceDAO.searchDevice(device);
							HttpServletResponse httpServletResponse = (HttpServletResponse) response;
							request.getSession().setAttribute("statusmsg","Update success !");
							request.getSession().setAttribute("searchedDevice", device);
							httpServletResponse.sendRedirect(request.getContextPath() + "/updateDevice.jsp");
						} else {
							request.getSession().setAttribute("statusmsg","device update faliure !");
						}

					} catch (Exception e) {
						System.out.println("update failed ? --------------   "+e.getMessage());
					}
				}

			} else if (Constants.COMMAND_DELETE_DEVICE.equals(userCommand)) {
				// delete existing user
				DeviceDTO device = getDevide(request);

				if (!DeviceValidator.isValidDevice(device)) {
					HttpServletResponse httpServletResponse = (HttpServletResponse) response;
					httpServletResponse.sendRedirect(request.getContextPath()+ "/deleteDevice.jsp");
				} else {
					try {
						System.out.println(" DeviceServlet user valid ");

						int delete = deviceDAO.deleteDevice(device);
						System.out.println("delete successful ? --------------   "	+ delete);

						if (delete == 1) {

							HttpServletResponse httpServletResponse = (HttpServletResponse) response;
							request.getSession().setAttribute("statusmsg","Device deletion success !");
							request.getSession().setAttribute("deletedDevice", device);
							httpServletResponse.sendRedirect(request.getContextPath() + "/deleteDevice.jsp");
						}  else {
							request.getSession().setAttribute("statusmsg","Device delete  faliure !");
						}

					} catch (Exception e) {
						System.out.println(" DeviceServlet user valid " + e.getMessage());
					}
				}

			}
		}
	}

	private DeviceDTO getDevide(HttpServletRequest request) {

		DeviceDTO device = new DeviceDTO();

		String deviceId = request.getParameter("deviceId") == null ? null: request.getParameter("deviceId").trim();
		String imei = request.getParameter("IMEI") == null ? null : request.getParameter("IMEI").trim();
		String description = request.getParameter("description") == null ? null	: request.getParameter("description").trim();
		String deviceDid = request.getParameter("deviceDid") == null ? null : request.getParameter("deviceDid").trim() ;
		
		try{
			
			device.setDeviceId(deviceId);
			device.setImei(imei);
			device.setDescription(description);
			device.setDeviceDid(Integer.parseInt(deviceDid));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		

		return device;
	}

}
