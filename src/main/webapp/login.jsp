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
		
		<h2>Log In</h2>
		
		<form action="login" method="post">
			
			<div><label for="username">Username</label> <input type="text" name="username" class="form-control"></div><br>

			<div> <label for="password">Password</label> <input type="password" name="password" class="form-control"></div><br>
			
			<button type="submit" class="button">Login</button> <br> <br>
			
			<h6 style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; font-size: medium;"> New User?<a style="text-decoration: none" href="<%=request.getContextPath()%>/signup.jsp"> &nbsp;Sign Up</a></h6>
	
		</form>
	</div>
</body>
</html>