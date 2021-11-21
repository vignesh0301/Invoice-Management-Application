<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Invoice App</title>
<link rel="stylesheet" href="./styles.css">

</head>
<body>

	<div class="container card" style="margin-top: 20px; padding-top: 50px; padding-left: 50px; padding-right: 50px; margin: 10%; margin-top: 60px; margin-bottom: 0px;">
	
		<h2>Sign Up</h2>
		
		<form id="signup" action="signup" method="post">
		
			<div><label for="username">Username</label> <input type="text" id="username"	name="username" class="form-control"></div><br>
			
			<div> <label for="company">Company Name</label> <input type="text" id="company" name="company" class="form-control"></div><br>
			
			<div> <label for="email">Email</label> <input type="email" name="email" id="email" class="form-control"></div><br>
			
			<div><label for="password">Password</label> <input type="password" id="password" name="password"></div><br>
			
			<button type="button" onclick="signUp()" class="button">SignUp</button><br> <br>
			
			<h6 style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; font-size: medium;"> Already have an account?<a style="text-decoration: none" href="<%=request.getContextPath()%>/login.jsp"> &nbsp;Log In</a></h6>
		
		</form>
	</div>
</body>
<script>
     function signUp(){
    	 if(document.getElementById("username").value.length<3){
    		alert("Username should be atleast 3 letters");
    	}
    	else if(document.getElementById("password").value.length<3){
 			alert("Password should be atleast 8 letters");
 		}
    	else if(document.getElementById("company").value.length<1){
 			alert("Please enter your Company Name");
 		}
    	else if(document.getElementById("email").value.length<1){
 			alert("Please enter your email address");
 		}
    	else{
    		 document.getElementById("signup").submit();
    	 }
    	 
     }
</script>
</html>