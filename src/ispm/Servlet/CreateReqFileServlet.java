package ispm.Servlet;

import ispm.controller.DocPageCounter;
import ispm.controller.IdentifyReq;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.pdf.PdfReader;

/**
 * Servlet implementation class CreateReqFileServlet
 */
@WebServlet("/CreateReqFileServlet")
public class CreateReqFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateReqFileServlet() {
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
		String selectedItem = request.getParameter("droplist").toString();
		System.out.println(selectedItem);
		String  filepath = "c:/uploads/"+selectedItem;
		System.out.println(filepath);
		int pagecount = 0;
		PdfReader reader = null;
		DocPageCounter pagec = new DocPageCounter(reader, filepath);
		pagecount =  pagec.pagecount();
		IdentifyReq ir = new IdentifyReq();
		for(int i=1;i<pagecount+1;i++)
		{
			ir.createreqFile(filepath, i);
		}
		System.out.println("text file created successfully!!!!!!");
		response.setHeader("Refresh", "0; URL=http://localhost:8080/Gims/pages/requirementcollector.jsp");
	}

}
