package com.tracking.action;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tracking.token.*;
import com.tracking.utils.SFTSUtil;
import com.tracking.utils.UserRole;
import com.tracking.connectivity.*;
import com.tracking.domain.DeviceDTO;
import com.tracking.domain.UserDTO;

//@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class LoginServerlet extends HttpServlet 
{



	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Token token = null;
        
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        UserDTO user = new UserDTO(userName , password);
        
        request.getSession().setAttribute("statusmsg", null);
        request.getSession().removeAttribute("allUsers");
        request.getSession().removeAttribute("allDevices");
          
        System.out.println("userName "+userName+ "  password "+password);
        
        if (userName == null || password == null ) 
        {           
            response.sendRedirect(request.getContextPath()  + "/Map1.jsp");
        } 
        else 
        {
            try 
            {
            	UserDAO userDAO =  new UserDAO();
				UserDTO user1 = userDAO.login(user);
				System.out.println("Login ....  user1 "+user1);
				
				if (user1 != null) {
                    HttpSession httpSession = request.getSession(true);
                    httpSession.setAttribute("userSession", user1);
                    httpSession.setAttribute("user_name", user1.getUserName());
                    httpSession.setAttribute("first_name", user1.getFirstName());
                    httpSession.setAttribute("last_name", user1.getLastName());
                    httpSession.setAttribute("birth_date", user1.getBirthDate());
                    httpSession.setAttribute("country", user1.getCountry());
                    httpSession.setAttribute("gender", user1.getGender());
                    httpSession.setAttribute("user_image", user1.getUserImage());
                    httpSession.setAttribute("userrole_roledid", user1.getRole());
                    httpSession.setMaxInactiveInterval(720000);

                    if(user1.getRole() == UserRole.ADMIN.getRoleDid()){
                    	
                    	// populate all users to populate in the table
                    	ArrayList<UserDTO> userList = userDAO.getAvailableUsers();
                    	System.out.println("all users @ login user page "+userList);
                    	if(SFTSUtil.isNotEmpty(userList)){
                    		httpSession.setAttribute("allUsers", userList);
                    	}
                    
                    	// populate all users to populate in the table
                    	DeviceDAO deviceDAO = new DeviceDAO();
                    	ArrayList<DeviceDTO> deviceList = deviceDAO.getAvailableDevices();
                    	System.out.println(" all devices @ login user page "+deviceList);
                    	if(SFTSUtil.isNotEmpty(deviceList)){
                    		httpSession.setAttribute("allDevices", deviceList);
                    	}
                    
                    }
                  
                    
                    httpSession.setAttribute("currentUser", user1);                    
                              
                    response.sendRedirect(request.getContextPath() + "/Map1.jsp");
                } 
                else 
                {
                    token = new MessageToken();
                    token.setCode(Token.UNAUTHORIZED);
                    token.setMessage("Login failed");
                    System.out.println("PM LOGIN FACADE " + token.getCode());
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                }
            } 
            catch (NumberFormatException ex) 
            {
                Logger.getLogger(LoginServerlet.class.getName()).log(Level.WARNING, null, ex);
                token = new MessageToken();
                token.setCode(Token.UNAUTHORIZED);
                token.setMessage("Login failed");
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }catch(Exception e )
            {
            	 Logger.getLogger(LoginServerlet.class.getName()).log(Level.WARNING, null, e);
                 token = new MessageToken();
                 token.setCode(Token.UNAUTHORIZED);
                 token.setMessage("Login failed");
                 response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
    //        response.setContentType("applicaton/json;charset=UTF-8");
            //nee d try-carch
     //       PrintWriter out = response.getWriter();
       //     gson.toJson(token, out);
    //        out.flush();

        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
    	System.out.println("doGET invoked");
        processRequest(request, response);
    }

 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
    	System.out.println("doPOST invoked");
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() 
    {
        return "Short description";
    }
    // </editor-fold>

}