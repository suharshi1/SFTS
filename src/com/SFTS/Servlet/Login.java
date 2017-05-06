package com.SFTS.Servlet;

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
import com.SFTS.Token.*;
import com.SFTS.Base.Entities.*;
import com.SFTS.ConnectionManager.*;


//@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet 
{



	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Token token = null;
        
        String userName = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User(userName , password);
        
    /*   String strObject = request.getParameter("data");
        Gson gson = Converters.registerLocalDateTime(new GsonBuilder()).create();
        User user = gson.fromJson(strObject, User.class);*/
        if (userName == null ) 
        {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
           httpServletResponse.sendRedirect(request.getServletContext().getContextPath() + "/Map1.jsp");
        } 
        else 
        {
            try 
            {
              
				User user1 = new UserDAO().login(user);
				System.out.println("Login .... else user1"+user1);
				
				if (user1 != null) {
                    HttpSession httpSession = request.getSession(true);
                    httpSession.setAttribute("userSession", user1);
                    httpSession.setAttribute("user_name", user1.getUserName());
                    httpSession.setAttribute("first_name", user1.getFirstname());
                    httpSession.setAttribute("last_name", user1.getLastname());
                    httpSession.setAttribute("birth_date", user1.getBirthdate());
                    httpSession.setAttribute("country", user1.getCountry());
                    httpSession.setAttribute("gender", user1.getGender());
                    httpSession.setAttribute("user_image", user1.getUserImage());
                    httpSession.setMaxInactiveInterval(720000);

                    System.out.println(" LOGIN FACADE " + user1.getCountry());

                    List<UserServlet> tempUserList = new ArrayList<UserServlet>();
                    token = new PayloadListToken(tempUserList);
                    token.setCode(Token.SUCCESS);
                    token.setMessage("Login success !");
                    System.out.println(" Token LOGIN FACADE " + token.getCode());
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.sendRedirect(request.getServletContext().getContextPath() + "/Map1.jsp");
                } 
                else 
                {
                    token = new MessageToken();
                    token.setCode(Token.UNAUTHORIZED);
                    token.setMessage("Login failed");
                    System.out.println("PM LOGIN FACADE " + token.getCode());
                }
            } 
            catch (NumberFormatException ex) 
            {
                Logger.getLogger(Login.class.getName()).log(Level.WARNING, null, ex);
                token = new MessageToken();
                token.setCode(Token.UNAUTHORIZED);
                token.setMessage("Login failed");
            }

            response.setContentType("applicaton/json;charset=UTF-8");
            //nee d try-carch
            PrintWriter out = response.getWriter();
       //     gson.toJson(token, out);
            out.flush();

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