/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracking.action;

import com.tracking.connectivity.*;
import com.tracking.domain.User;
import com.SFTS.Token.*;
import com.tracking.utils.Constants;
import com.tracking.utils.UserValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UserServlet extends HttpServlet 
{


	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Token token = null;
        System.out.println("Process request @ User servlet ");
        String fName  = request.getParameter("inputFName");
        String lName  = request.getParameter("inputLName");
        String address = request.getParameter("inputAddress");
        String dob = request.getParameter("inptDoB");
        String role = request.getParameter("inputRole");
        String contactNo = request.getParameter("inputContactNo");
        String email = request.getParameter("inputEmail");
        String password = request.getParameter("password");
        String command = request.getParameter("actionCommand");
        String userName = request.getParameter("userName");
        
        
        System.out.println( " command = " +command);
        User user = new User();
        user.setFirstname(fName);
        user.setLastname(lName);
        user.setAddress(address);
        user.setDob(dob);
        user.setPassword(password);
        user.setEmail(email);
        user.setUserName(userName);
        
        
        request.getSession().setAttribute("currentUser", user);
        
        System.out.println( " fName = " +fName+ " lName "+lName + " address "+address + " dob "+dob);
        
        if (!UserValidator.isValidUser(user))
        {
        	System.out.println( " UserServlet if = " );
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//            httpServletResponse.sendRedirect(request.getServletContext().getContextPath() + "/addUser.jsp");
        } 
        else 
        {
        	 try 
            {
        		 System.out.println( " UserServlet else = " );
        		 
        		 if(command != null && command.equals(Constants.COMMAND_ADD_USER)){
        			int add = new UserDAO().addUser(user);
        			if (add == 1){
        				   List<UserServlet> tempUserList = new ArrayList<UserServlet>();
                           token = new PayloadListToken(tempUserList);
                           token.setCode(Token.SUCCESS);
                           token.setMessage("User creation success !");
                           System.out.println(" Token LOGIN FACADE " + token.getCode());
                           HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                           request.getSession().setAttribute("statusmsg","User creation success !" );
  //                         httpServletResponse.sendRedirect(request.getServletContext().getContextPath() + "/addUser.jsp");
        			}else {
        				request.getSession().setAttribute("statusmsg","User creation faliure !" );
        				request.getSession().setAttribute("statusmsg","User creation success !" );
        			}
                 	System.out.println("add successful ?  "+add);
        		 }
            	             
            }catch(Exception e ){
            	
            }
        }
        }
	
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() 
    {
        return "Short description";
    }// </editor-fold>

}

