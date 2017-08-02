$(document).ready(function ()
{
	
});

function login()
{
    var username = $('#username').val();
    var password = $('#password').val();

    if (username.length === 0 || password.length === 0)
    {
        document.getElementById("validationmsg").innerHTML = "<h5 style=\"color:red\"><strong>Please Enter Your Username and Password to SignIn!</strong></h5><br/><br/>";
    } 
    else 
    {
        var loginInfo = {username: username, password: password};

        $.when($.post('/SFTS/Login', {cmd: 'LOGIN', data: JSON.stringify(loginInfo)}, function (response) 
        	{

            console.log(response);

            if (response.code === 200)
            {                
                window.location = "/SFTS/Map1.jsp";
            }
            else
            {
                document.getElementById("validationmsg").innerHTML = "<h5 style=\"color:red\"><b>Please Enter a valid Username and Password!</b></h5>";
            }
        }));
    }
}

function logout(){
	
	console.log("loadDevices ");
	console.log("loadDevices ");
	console.log("loadDevices ");
	// load the device drop down list
	$.ajax({  
	    type: "GET",  
	    url: "ajaxCommands",  
	    data: "command=logOutUser",  
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
	
}