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