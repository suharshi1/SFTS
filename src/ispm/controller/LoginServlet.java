package ispm.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Login log = new Login();
		String username = request.getParameter("username").toString();
		String password = request.getParameter("password").toString();
		//boolean login_status = (boolean) log.setLogin(username, password);
		String letter = log.setLogin(username,password);
		if(letter.equals("c")||letter.equals("e"))
		{			
			response.getWriter().println("success!!!");
			System.out.println("success");
			request.setAttribute("username", " ");
			request.setAttribute("password", " ");
			if(letter.equals("c"))
			getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);
			else
				getServletContext().getRequestDispatcher("/index1.jsp").forward(request,response);	
		}
		else
		{
			//response.getWriter().println("Failed");
			System.out.println(letter);
			//RequestDispatcher req;
			//req = request.getRequestDispatcher("/pages/examples/login.jsp");
			//req.forward(request, response);
			request.setAttribute("errorMessage", "Invalid user or password");
			response.setHeader("Refresh", "0; URL=http://localhost:8080/Gims/pages/examples/login.jsp");
		}
		
	}

}
