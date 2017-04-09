package ispm.Servlet;

import ispm.controller.DocPageCounter;
import ispm.controller.MongoConnection;
import ispm.controller.MongoDAO;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.itextpdf.text.pdf.PdfReader;
import com.mongodb.DB;
import com.mongodb.DBCollection;

/**
 * Servlet implementation class MonInsertDocServlet
 */
@WebServlet("/MonInsertDocServlet")
public class MonInsertDocServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String UPLOAD_DIRECTORY = "C:/uploads";
	private String filename = null;
	private String name_file = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonInsertDocServlet() {
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
		//process only if its multipart content
        if(ServletFileUpload.isMultipartContent(request)){
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                                         new DiskFileItemFactory()).parseRequest(request);
              
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        String name = new File(item.getName()).getName();
                        item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
                        System.out.println("donneeeeee");
                        System.out.println(UPLOAD_DIRECTORY + File.separator + name);
                        filename = UPLOAD_DIRECTORY + "/" + name;
                        name_file = name;
                        System.out.println(filename);
                    }
                }
           
               //File uploaded successfully
               request.setAttribute("message", "File Uploaded Successfully");
            } catch (Exception ex) {
               request.setAttribute("message", "File Upload Failed due to " + ex);
            }          
         
        }else{
            request.setAttribute("message",
                                 "Sorry this Servlet only handles file upload request");
        }
    
        request.getRequestDispatcher("/pages/insertdoc.jsp").forward(request, response);
     


       //Read more: http://javarevisited.blogspot.com/2013/07/ile-upload-example-in-servlet-and-jsp-java-web-tutorial-example.html#ixzz4B9i7tcHv
		

    	DB db = null;
    	PdfReader reader = null;
    	DBCollection docCollection = null;
    	int pagecount = 0;
    	String filepath = filename;
    	DocPageCounter pc = null;
    	MongoConnection mg = null;
    	int did = 0;//this has to be automated for every single project and should differntiate from project by project
    	String projectId = "ABC1";//has to capture this from the interface
        MongoDAO ob = new MongoDAO(db,reader,docCollection,pagecount,filepath,pc,mg,did,projectId,name_file);
		ob.insertDocument("mydb", "documents");
		response.setHeader("Refresh", "0; URL=http://localhost:8080/Gims/pages/insertdoc.jsp");
	}

}
