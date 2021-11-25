<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Invoice App</title>
<link rel="stylesheet" href="./styles.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<header>
		<ul>
			<li><a class="active" href="<%=request.getContextPath()%>">Invoice App</a></li>
			<li><a href="<%=request.getContextPath()%>/home">Invoices</a></li>
			<li><a href="<%=request.getContextPath()%>/newinvoice">New Invoice</a></li>
		    <li><a href="<%=request.getContextPath()%>/viewcustomer">View Customers</a></li>
			<li><a href="<%=request.getContextPath()%>/addcustomer.jsp">New Customer</a></li>
			<li><a href="<%=request.getContextPath()%>/items">Items</a></li>
			<li><a href="<%=request.getContextPath()%>/additem.jsp">New Item</a></li>
			<li><a href="<%=request.getContextPath()%>/settings.jsp">Settings</a></li>
			
			<li><a class="logout" href="<%=request.getContextPath()%>/logout">Logout</a></li>
		</ul>
	</header>

	<div class="container card" style=" padding-top: 50px; padding-left: 50px; padding-right: 50px;  margin-top: 60px; margin-bottom: 0px;">
		
		<h2>New Customer</h2>
		<form id="cust" action="editcustomer" method="post">

			<div>
				<label for="name" class="red">Customer Name *</label> <input value="<%=request.getParameter("name") %>" type="text" id="name" name="name" class="form-control">
			</div><br>

			<div>
				<label for="email" class="red">Customer Email *</label> <input value="<%=request.getParameter("email") %>" id="email" type="email" name="email" class="form-control">
			</div><br>
		
			<div>
				<label for="address">Address</label> <input value="<%=request.getParameter("address") %>" type="text" name="address" class="form-control">
			</div><br>

			<div>
				<label for="phone">Phone Number</label> <input value="<%=request.getParameter("phone") %>" type="number" name="phone" class="form-control">
			</div><br>

			<input type="hidden" name="id" value="<%=request.getParameter("id") %>">
			<button type="button" onclick="checkForm()" class="button">Edit</button><br><br><br>
			
		</form>
	</div>
</body>
<script>
function checkForm(){
	
	if(document.getElementById("name").value.length<=0)
		alert("Name cannot be empty"); 
	else if(document.getElementById("email").value.length<=0) 
		alert("Please enter Email Address"); 
	else{
		var re = /\S+@\S+\.\S+/;
	    if(re.test(document.getElementById("email").value))
	        document.getElementById("cust").submit();
	    else
	    	alert("Please enter a valid email address");
	}
	
}
</script>
</html>