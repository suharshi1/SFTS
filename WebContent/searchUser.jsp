<!DOCTYPE html>
<%@ page import="java.util.ArrayList"%>
<%@page import="com.tracking.domain.UserDTO"%>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Admin| Dashboard</title>
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

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
<script src="http://maps.google.com/maps?file=api&v=2&key=AIzaSyBP5wM7FziIOyjASsYufkUSHrw2cXkdrtA"
	type="text/javascript"></script>
<script type="text/javascript">
	function load() { 
		console.log("load function 222 ");
		// load the device drop down list
		$.ajax({  
		    type: "GET",  
		    url: "ajaxCommands",  
		    data: "command=loadDevices",  
		    success: function(result){  
		    	console.log("loadDevices ");
		    	console.log(result);
		    	console.log (result.DeviceArray.length);
		    	var select = document.getElementById("device");
		        var option1 = document.createElement("option");
	    	    option1.text = "-- Select --";
	    	    option1.value = -1;	    	    
	    	    select.add(option1);
	    	    console.log(result);
		    	for (var i = 0; i < result.DeviceArray.length; i++) {		    	    
		    	    var option = document.createElement("option");
		    	    option.text = result.DeviceArray[i].description;
		    	    option.value = result.DeviceArray[i].deviceDid;
		    	    select.add(option);
		    	}
		      
		    },failuer:function(result){
				console.log ("failed request ");
		    }                
		  });
		
		
		// load the role drop down list
		$.ajax({  
		    type: "GET",  
		    url: "ajaxCommands",  
		    data: "command=loadRoles",  
		    success: function(result){  
		    	console.log("loadRoles ");		    	
		    	console.log(result);
		    	console.log (result.RoleArray.length);
		    	var select = document.getElementById("role");
		        var option1 = document.createElement("option");
	    	    option1.text = "-- Select --";
	    	    option1.value = -1;	    	    
	    	    select.add(option1);
	    	    
		    	for (var i = 0; i < result.RoleArray.length; i++) {		    	    
		    	    var option = document.createElement("option");
		    	    option.text = result.RoleArray[i].description;
		    	    option.value = result.RoleArray[i].roleDid;
		    	    select.add(option);
		    	}
		      
		    },failuer:function(result){
				console.log ("failed request ");
		    }                
		  });
		
		
		
		if (GBrowserIsCompatible()) { 
			var map = new GMap2(document.getElementById("world-map2")); 
			map.setCenter(new GLatLng(7.8731, 80.7718), 7); 
			map.addControl(new GSmallMapControl()); 
			map.addControl(new GOverviewMapControl());
		} 	
		
	} 
</script>
<%

UserDTO currentUser = (UserDTO) session.getAttribute("userSession");
String userFName = "" , userLName = "" ;

if( currentUser != null ){
	
	userFName = currentUser.getFirstName();
	userLName = currentUser.getLastName();
	
}



int userDid = -1 ; 
UserDTO user = (UserDTO) session.getAttribute("searchedUser");
String firstName = "";
String lastName = "";
String address = "";
String mobileNumber = "";	
String userName = "";
String dob = "";
String email = "";
String password = "";

if (user != null ){
	
	firstName =  user.getFirstName() == null ? "" : user.getFirstName() ;
	lastName = user.getLastName() == null ? "" : user.getLastName();
	address = user.getAddress1() == null ? "" : user.getAddress1() ;
	mobileNumber = user.getContactNumber() == null ? "" : user.getContactNumber() ;
	userName = user.getUserName() == null ? "" : user.getUserName() ;
	dob = user.getDateOfBirth() == null ? "" : user.getDateOfBirth();
	email = user.getEmail() == null ? "" : user.getEmail()  ;
	password = user.getPassword()  == null ? "" : user.getPassword() ;
	userDid = user.getUserId();
}

%>
</head>
<body onload="load()" class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <header class="main-header">
    <!-- Logo -->
    <a href="index2.html" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>A</b>LT</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>SFTS</b>SFTS</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>

      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <!-- Messages: style can be found in dropdown.less-->
          <li class="dropdown messages-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <i class="fa fa-envelope-o"></i>
              <span class="label label-success"></span>
            </a>
            <ul class="dropdown-menu">
              <li class="header"></li>
              <li>
                <!-- inner menu: contains the actual data -->
                <ul class="menu">
                </ul>
              <li class="footer"><a href="#">See All Messages</a></li>
            </ul>
          </li>
          <!-- Notifications: style can be found in dropdown.less -->
          <li class="dropdown notifications-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <i class="fa fa-bell-o"></i>
              <span class="label label-warning"></span>
            </a>
            <ul class="dropdown-menu">
                <!-- inner menu: contains the actual data -->
                <ul class="menu">
                </ul>
              <li class="footer"><a href="#">View all</a></li>
            </ul>
            </li>
          </li>
          <!-- Tasks: style can be found in dropdown.less -->
          <li class="dropdown tasks-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <i class="fa fa-flag-o"></i>
              <span class="label label-danger"></span>
            </a>
            <ul class="dropdown-menu">
              <li class="header"></li>
              <li>
                <!-- inner menu: contains the actual data -->
                <ul class="menu">
                    </a>
                  </li>

                </ul>
              </li>
              <li class="footer">
                <a href="#">View all tasks</a>
              </li>
            </ul>
          </li>
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
              <span class="hidden-xs"><%= userFName + " " + userLName %></span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">

                <p>
                  <%= userFName + " " + userLName %>
                  <small>Member since Nov. 2008</small>
                </p>
              </li>
              <!-- Menu Body -->
              <li class="user-body">
                <div class="row">
                  <div class="col-xs-4 text-center">
                    <a href="#">Followers</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Sales</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Friends</a>
                  </div>
                </div>
                <!-- /.row -->
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="#" class="btn btn-default btn-flat">Profile</a>
                </div>
                <div class="pull-right">
                  <a href="#" class="btn btn-default btn-flat">Sign out</a>
                </div>
              </li>
            </ul>
          </li>
          <!-- Control Sidebar Toggle Button -->
          <li>
            <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
          </li>
        </ul>
      </div>
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p><%= userFName + " " + userLName %></p>
          <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
        </div>
      </div>
      <!-- search form -->
      <form action="#" method="get" class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="Search...">
              <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                </button>
              </span>
        </div>
      </form>
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="header">MAIN NAVIGATION</li>
         <li class="treeview">
       <li>
          <a href="Map1.jsp">
            <i class="fa fa-th"></i> <span>Manage Users</span>
            <small class="label pull-right bg-green"></small>
          </a>
        </li>
        <li>
          <a href="ManageDevice.jsp">
            <i class="fa fa-laptop"></i> <span>Manage Device</span>
            <small class="label pull-right bg-green"></small>
          </a>
        </li>
        
       <li>
          <a href="addLandmark.jsp">
            <i class="fa fa-map"></i> <span>Add Landmarks</span>
            <small class="label pull-right bg-green"></small>
          </a>
        </li>
          
        
        <li class="treeview">
          <a href="Reports.jsp">
            <i class="fa fa-dashboard"></i> <span>Reports</span>
             <i class="fa fa-angle-left pull-right"></i>
          </a>
          <ul class="treeview-menu">
            <li class="active"><a href="index.html"><i class="fa fa-circle-o"></i> User Wise</a></li>
            <li><a href="index2.html"><i class="fa fa-circle-o"></i>Date Wise</a></li>
            <li><a href="index2.html"><i class="fa fa-circle-o"></i>Month Wise</a></li>
            <li><a href="index2.html"><i class="fa fa-circle-o"></i>Device Status</a></li>
          </ul>
        </li>  
        
        <li>
          <a href="pages/calendar.html">
            <i class="fa fa-calendar"></i> <span>Calendar</span>
            <small class="label pull-right bg-red">3</small>
          </a>
        </li>
        
        <li>
          <a href="pages/mailbox/mailbox.html">
            <i class="fa fa-envelope"></i> <span>Mailbox</span>
            <small class="label pull-right bg-yellow">12</small>
          </a>
        </li>

        <li class="header">LABELS</li>
        <li><a href="#"><i class="fa fa-circle-o text-red"></i> <span>Important</span></a></li>
        <li><a href="#"><i class="fa fa-circle-o text-yellow"></i> <span>Warning</span></a></li>
        <li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>Information</span></a></li>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
       Search Users
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Manage Users</a></li>
        <li><a href="#">Search</a></li>
        <li class="active">User Profile</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">

      <div class="row">
        <div class="col-md-3">

         <!-- Profile Image -->
          <div class="">
   

              
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->

          <!-- About Me Box -->
          <div class="">
          
           
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
        </div>
        <!-- /.col -->
        
      
        
    <div class="col-md-9">
          <div class="nav-tabs-custom">
            <ul class="nav nav-tabs">
              <li class="active"><a href="#settings" data-toggle="tab">Search</a></li>
            </ul>
   
      
        
      
            
            <div class="tab-content">
              <div class="active tab-pane" id="settings">
                
                <form class="form-horizontal"  action ="user" method = "POST" >
               		<input type="hidden" name="user_command" id="user_command" value="searchUser"></input>
	             	  <div class="form-group form-group-sm">
	                    <label for="inputName" class="col-sm-2 control-label">User Name</label>
	                    <div class="col-sm-10">
	                      	<div class="input-group ">
	                    		<input type="text" class="form-control" id="searchName" name="searchName" value="<%=userName%>" placeholder="User Name" />
					      			<span class="input-group-btn">
					        		<button class="btn btn-sm" type="submit">Go!</button>
					      			</span>
					    	</div><!-- /input-group -->
	                    </div><!-- /col-sm-10 -->                    
	                  	</div>               
               </form>
                    
     
                  
                    
                  
                  
                  
                  
                    
                   
                    <!--  All users table row -->
      <div class="box">
             <div class="box-body">
              <table id="usertable1" class="table table-bordered table-striped">
                <thead>
                <tr>                  
                  <th>First Name</th>
                  <th>Last Name</th>
                  <th>Address</th>
                  <th>Email</th>
                  <th>Contact No</th>
                  
                </tr>
                </thead>
                <tbody>
              <% 
               		// this is a scriplet 
               		/*
               		users list which is retrieved from the back end */
               		ArrayList <UserDTO> userList = (ArrayList<UserDTO>) request.getSession().getAttribute("allUsers");
	                if(userList != null){
	                	for( UserDTO user1 : userList ){

               %>
               
                <tr>
                <input type="hidden"  > </input>
                  <td><%=user1.getFirstName()%></td>
                  <td><%=user1.getLastName()%> </td>
                  <td><%=user1.getAddress1()%> </td>
                  <td><%=user1.getEmail()%> </td>
                  <td><%=user1.getContactNumber()%> </td>

                </tr>
                
                <%
	                	}
	                }
               		// end of for loop which iterates the users list
                %>
                
              
                </tbody>
                <tfoot>
               
                </tfoot>
              </table>
            
            <!-- /.box-body -->
          </div>
          
                
                   </form>
                  
              
              </div>
              <!-- /.tab-pane -->
            </div>
            <!-- /.tab-content -->
          </div>
          <!-- /.nav-tabs-custom -->
        </div>
        </div>
        
       
        <!-- /.col -->
      </div>
      <!-- /.row -->

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 2.3.3
    </div>
      <strong>Copyright &copy; 2017 <a href="http://beds.ac.uk/">University of Bedfordshire 2017</a>.</strong> All rights reserved.
    reserved.
  </footer>

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Create the tabs -->
    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
      <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
      <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
      <!-- Home tab content -->
      <div class="tab-pane" id="control-sidebar-home-tab">
        <h3 class="control-sidebar-heading">Recent Activity</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-birthday-cake bg-red"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

                <p>Will be 23 on April 24th</p>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-user bg-yellow"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Frodo Updated His Profile</h4>

                <p>New phone +1(800)555-1234</p>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-envelope-o bg-light-blue"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Nora Joined Mailing List</h4>

                <p>nora@example.com</p>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-file-code-o bg-green"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Cron Job 254 Executed</h4>

                <p>Execution time 5 seconds</p>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

        <h3 class="control-sidebar-heading">Tasks Progress</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                Custom Template Design
                <span class="label label-danger pull-right">70%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                Update Resume
                <span class="label label-success pull-right">95%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-success" style="width: 95%"></div>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                Laravel Integration
                <span class="label label-warning pull-right">50%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-warning" style="width: 50%"></div>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                Back End Framework
                <span class="label label-primary pull-right">68%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-primary" style="width: 68%"></div>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

      </div>
      <!-- /.tab-pane -->
      <!-- Stats tab content -->
      <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
      <!-- /.tab-pane -->
      <!-- Settings tab content -->
      <div class="tab-pane" id="control-sidebar-settings-tab">
        <form method="post">
          <h3 class="control-sidebar-heading">General Settings</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Report panel usage
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Some information about this general settings option
            </p>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Allow mail redirect
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Other sets of options are available
            </p>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Expose author name in posts
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Allow the user to show his name in blog posts
            </p>
          </div>
          <!-- /.form-group -->

          <h3 class="control-sidebar-heading">Chat Settings</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Show me as online
              <input type="checkbox" class="pull-right" checked>
            </label>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Turn off notifications
              <input type="checkbox" class="pull-right">
            </label>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Delete chat history
              <a href="javascript:void(0)" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
            </label>
          </div>
          <!-- /.form-group -->
        </form>
      </div>
      <!-- /.tab-pane -->
    </div>
  </aside>
  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- jQuery 2.2.0 -->
<script src="plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="bootstrap/js/bootstrap.min.js"></script>
<!-- FastClick -->
<script src="plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="dist/js/app.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="dist/js/demo.js"></script>

<!-- DataTables -->
<script src="../../plugins/datatables/jquery.dataTables.min.js"></script>
<script src="../../plugins/datatables/dataTables.bootstrap.min.js"></script>
<script>
  $(function () {
    $("#usertable1").DataTable();
   
  });
</script>
</body>





</html>