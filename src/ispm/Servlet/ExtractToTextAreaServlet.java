package ispm.Servlet;

import ispm.controller.ExtractToTextArea;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ExtractToTextAreaServlet
 */
@WebServlet("/ExtractToTextAreaServlet")
public class ExtractToTextAreaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExtractToTextAreaServlet() {
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
		ExtractToTextArea ex = new ExtractToTextArea();
		String requirements = ex.DirToTextArea();
		System.out.println(requirements);
		request.setAttribute("TextArea", "Helllloooo");
		//getServletContext().getRequestDispatcher("/pages/requirementcollector.jsp").forward(request,response);
		response.setHeader("Refresh", "0; URL=http://localhost:8080/Gims/pages/requirementcollector.jsp");
		
	}

}