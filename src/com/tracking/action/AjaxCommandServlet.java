package com.tracking.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tracking.connectivity.AdminDAO;
import com.tracking.connectivity.DeviceDAO;
import com.tracking.domain.DeviceDTO;
import com.tracking.domain.RoleDTO;

/**
 * Servlet implementation class AjaxCommandServlet
 */

public class AjaxCommandServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static String LOAD_DEVICE = "loadDevices";
	private final static String LOAD_ROLE = "loadRoles";
	private final static String LOG_OUT = "logOutUser";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxCommandServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /*
     * 
     * private int deviceDid;
	
	private String deviceId;
	
	private String description ;
	
	private String imei;
     */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String command = request.getParameter("command");
		
		System.out.println("AjaxCommandServlet ............ "+command);
		
		
		if(LOAD_DEVICE.equals(command)){
			JSONObject deviceJson      = new JSONObject();
			JSONArray  deviceArray = new JSONArray();
			JSONObject device;

			System.out.println("load devices");
			
			DeviceDAO deviceDAO = new DeviceDAO();
			try{
				List<DeviceDTO> devices = deviceDAO.getAvailableDevices();
				for(DeviceDTO d : devices){
					device = new JSONObject();
					
					device.put("deviceDid", d.getDeviceDid());
					device.put("deviceId", d.getDeviceId());
					device.put("description", d.getDescription());
					device.put("imei", d.getImei());
					deviceArray.put(device);								
				}	
				
				deviceJson.put("DeviceArray", deviceArray);
				response.setContentType("application/json");
				System.out.println("device json "+deviceJson);
				response.getWriter().write(deviceJson.toString());
				
				
			}catch(Exception sex){
				System.out.println("SQL Exception ");
			}
		
		}else if (LOAD_ROLE.equals(command)){
			JSONObject roleJson      = new JSONObject();
			JSONArray  roleArray = new JSONArray();
			JSONObject role;
			
			System.out.println("load roles");
			
			AdminDAO roleDAO = new AdminDAO();
			try{
				List<RoleDTO> roles = roleDAO.getUserRoles();
				for(RoleDTO r : roles){
					role = new JSONObject();
					
					role.put("roleDid", r.getRoleDid());
					role.put("description", r.getRoleId());
					
					roleArray.put(role);								
				}	
				
				roleJson.put("RoleArray", roleArray);
				response.setContentType("application/json");
				System.out.println("device json "+roleJson);
				response.getWriter().write(roleJson.toString());
				
				
			}catch(Exception sex){
				System.out.println("SQL Exception ");
			}
		}if(LOG_OUT.equals(command)) {
			request.getSession().removeAttribute("userSession");
			request.getSession().removeAttribute("allUsers");
			response.sendRedirect(request.getContextPath()+ "/login.jsp");
		}
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	
	public static void main (String [] args){
			JSONObject deviceJson      = new JSONObject();
			JSONArray  deviceArray = new JSONArray();
			JSONObject device;

			System.out.println("load devices");
			
			DeviceDAO deviceDAO = new DeviceDAO();
			try{
				List<DeviceDTO> devices = deviceDAO.getAvailableDevices();
				for(DeviceDTO d : devices){
					device = new JSONObject();
					
					device.put("deviceDid", d.getDeviceDid());
					device.put("deviceId", d.getDeviceId());
					device.put("description", d.getDescription());
					device.put("imei", d.getImei());
					deviceArray.put(device);								
				}	
				
				deviceJson.put("DeviceArray", deviceArray);
				
				System.out.println("device json "+deviceJson);
				
			}catch(SQLException sex){
				System.out.println("SQL Exception ");
			}catch(JSONException jex){
				System.out.println("JSON Exception ");
			}
		
	}
	
	
}
