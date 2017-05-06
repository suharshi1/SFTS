package com.SFTS.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.SFTS.Base.Entities.User;
import com.SFTS.ConnectionManager.UserDAO;
import com.SFTS.Token.MessageToken;
import com.SFTS.Token.Token;


@WebServlet(name = "EntityFacade", urlPatterns = {"/EntityFacade"})
public class EntityFacade extends HttpServlet {

    public enum Command 
    {
    	
    	Add
    }
    	 protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	            throws ServletException, IOException,  ParseException 
    	 {
    	       User user = null;
    		 	HttpSession session = request.getSession(true);
    	        HttpSession httpSession = request.getSession(true);
    	        Token token = null;
    	        if (session != null) {
    	            user = (User) session.getAttribute("userSession");
    	            if (user == null) {
    	                response.sendRedirect(request.getServletContext().getContextPath() + "/signin.jsp");
    	            }
    	        } else {
    	            response.sendRedirect(request.getServletContext().getContextPath() + "/signin.jsp");
    	        }
    	        
    	        try {
    	            String cmd = request.getParameter("cmd");
    	            String strObject;
    	            switch (EntityFacade.Command.valueOf(cmd)) {
    	            
    	            
    	 case Add:
    		    strObject = request.getParameter("data");
    		    User user1 = new UserDAO().login(user);
    		    HttpSession httpSession1 = request.getSession(true);
    		    httpSession1.setAttribute("userSession", user1);
    	//	    httpSession1.setAttribute("user_name", user1.getUsername());
    		    httpSession1.setAttribute("user_image", user1.getUserImage());
    		    httpSession1.setMaxInactiveInterval(720000);
    		//    token = EntityHelpers.register(user1);
    		    break;
    	            }
    		}
    	        catch (NullPointerException ex) {
    	            token = new MessageToken();
    	            token.setCode(Token.INTE_SERVER_ERROR);
    	            token.setMessage("Error occured during process, please contact system administrator ");
    	            Logger.getLogger(EntityFacade.class.getName()).log(Level.WARNING, null, ex);
    	        } catch (DateTimeParseException ex) {
    	            token = new MessageToken();
    	            token.setCode(Token.CONFLICT);
    	            token.setMessage("Invalid input parameters ");
    	            Logger.getLogger(EntityFacade.class.getName()).log(Level.WARNING, null, ex);
    	        } catch (NumberFormatException ex) {
    	            token = new MessageToken();
    	            token.setCode(Token.CONFLICT);
    	            token.setMessage("Invalid input parameters ");
    	            Logger.getLogger(EntityFacade.class.getName()).log(Level.WARNING, null, ex);
    	        }

    	        response.setContentType("applicaton/json;charset=UTF-8");
    	        //nee d try-carch
    	        PrintWriter out = response.getWriter();
    	        //gson.toJson(token, out);
    	        out.flush();
    	        
    	}
}
