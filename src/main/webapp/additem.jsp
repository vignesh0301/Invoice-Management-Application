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
			<li><a href="<%=request.getContextPath()%>/home">Home</a></li>
			<li><a href="<%=request.getContextPath()%>/addcustomer.jsp">New Customer</a></li>
			<li><a href="<%=request.getContextPath()%>/newinvoice">New 	Invoice</a></li>
			<li><a href="<%=request.getContextPath()%>/additem.jsp">New Item</a></li>
			<li><a class="logout" href="<%=request.getContextPath()%>/logout">Logout</a></li>
		</ul>
	</header>

	<div class="container" style="margin-top: 10px; padding-top: 10px; padding-left: 20px; padding-right: 20px; margin: 10%; margin-top: 30px; margin-bottom: 0px;">
		<form action="items" method="get">
			<button type="submit" class="button" style="margin-bottom: 15px;">View Items</button>
		</form>
	</div>


	<div class="container card" style=" padding-top: 50px; padding-left: 50px; padding-right: 50px;">
		
		<h2>New Item</h2>
		<form id="form" action="additem" method="post">

			<div>
				<label for="itemname" class="red">Item Name *</label> <input id="itemname" type="text" name="itemname" class="form-control">
			</div><br>

			<div>
				<label for="type">Type</label> <select name="type">
					<option value="goods">Goods</option>
					<option value="services">Services</option>
				</select>
			</div><br>

			<div>
				<label for="costprice" class="red">CostPrice *</label> <input name="costprice" type="number" id="cp" class="form-control" pattern="^\d*(\.\d{0,2})?$">
			</div><br>

			<div>
				<label for="sellingprice" class="red">Selling Price *</label> <input name="sellingprice" type="number" id="sp" class="form-control" pattern="^\d*(\.\d{0,2})?$">
			</div><br>

			<div>
				<label for="description">Description</label> <input type="text" name="description" class="form-control">
			</div><br>


			<button type="button" onclick="checkForm()" class="button">Add</button><br><br><br>
		</form>
	</div><br><br><br><br>
</body>
<script>
function checkForm(){

	if(document.getElementById("itemname").value.length<=0){
		alert("Please enter item name");
	}
	else if(document.getElementById("cp").value.length<=0 && document.getElementById("sp").value.length<=0){
		alert("Please enter cost price or selling price")
	}
	else{
		if(document.getElementById("cp").value.length<=0 ) document.getElementById("cp").value=0; 
		if(document.getElementById("sp").value.length<=0 ) document.getElementById("sp").value=0; 

		
		document.getElementById("form").submit()
	}
}
</script>
</html>