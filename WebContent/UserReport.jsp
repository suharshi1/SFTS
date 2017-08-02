<%@ page contentType="application/pdf"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page import="net.sf.jasperreports.engine.*"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.io.FileNotFoundException"%>
<%@ page import="java.io.InputStream"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Driver"%>
<%@ page import="net.sf.jasperreports.engine.export.*"%>
<%@ page import="java.util.*"%>

<%
 	 Connection con = null;
 	 
 	 try{
 	 
 		 //Connecting to mysql database
 	 	 Class.forName("com.mysql.jdbc.Driver");
 	 	 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sfts?user=root&password=root");
 	 
 	 	 //Loading Jasper Report File from Local file System
 	 	 String jrxmlFile1 =session.getServletContext().getRealPath("/UserReport.jrxml");
 	 	 
 	 	 
 	 	 InputStream input1 = new FileInputStream(new File(jrxmlFile1));
 	 	 
 	 	 //Generating the report
 	 	 JasperReport jsp1 = JasperCompileManager.compileReport(input1);
 	 	 JasperPrint jPrint1 = JasperFillManager.fillReport(jsp1,null,con);
 	 	 
 	 	 //Exporting the Report as a PDF
 	 	 JasperExportManager.exportReportToPdfStream(jPrint1,response.getOutputStream());
 	 	 response.getOutputStream().flush();
 	 	 response.getOutputStream().close();
 	 	 
 	 }catch(Exception e){
 		e.printStackTrace();
 	 }finally{
 		 
 		 if(con != null)
 		 	con.close();	 
 		 
 	 }
 	 
 	%> 	  
 	 	  