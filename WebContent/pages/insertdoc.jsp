<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Sales Force Tracking System | Dashboard</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="plugins/iCheck/flat/blue.css">
  <!-- Morris chart -->
  <link rel="stylesheet" href="plugins/morris/morris.css">
  <!-- jvectormap -->
  <link rel="stylesheet" href="plugins/jvectormap/jquery-jvectormap-1.2.2.css">
  <!-- Date Picker -->
  <link rel="stylesheet" href="plugins/datepicker/datepicker3.css">
  <!-- Daterange picker -->
  <link rel="stylesheet" href="plugins/daterangepicker/daterangepicker-bs3.css">
  <!-- bootstrap wysihtml5 - text editor -->
  <link rel="stylesheet" href="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
  
  <script src="D:/SLIIT/4th year/CDAP/RESEARCH/AdminLTE-master/javascript plugins/dropzone.js"></script>

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

</head>

<body class="hold-transition skin-blue sidebar-mini">

 <form action="/Gims/MonInsertDocServlet" class="dropzone" method="post" enctype="multipart/form-data"/>
   <div>
     <input name="file" type="file" >
        <input type="submit" value="upload"/>
   </div>
 </form>
 <select class="re-dropdown">
         <%@page import ="ispm.controller.FileLoader" %>
         <%@page import ="java.io.File" %>
         <% 
         FileLoader fl = new FileLoader();
         File[] listOfFiles = fl.getFilestoList();
         for(int count=0; count<listOfFiles.length; count++){
        	 //System.out.println(listOfFiles[count].getName());
        	 String file = listOfFiles[count].getName();
        	 %>
            <option value="<%=file%>"><%=file%></option>
              System.out.println(listOfFiles[count].getName());
         <%} %>
  		<option value="volvo">Volvo</option>
  		<option value="saab">Saab</option>
  		<option value="opel">Opel</option>
  		<option value="audi">Audi</option>
		</select>


</body>
</html>