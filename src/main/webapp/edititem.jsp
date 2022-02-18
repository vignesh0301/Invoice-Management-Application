<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Invoice App</title>
<link rel="stylesheet" href="./styles.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

	<div class="container" style="margin-top: 10px; padding-top: 10px; padding-left: 20px; padding-right: 20px; margin: 10%; margin-top: 30px; margin-bottom: 0px;">
		<form action="items" method="get">
			<button type="submit" class="button" style="margin-bottom: 15px;">View Items</button>
		</form>
	</div>


	<div class="container card" style=" padding-top: 50px; padding-left: 50px; padding-right: 50px;">
		
		<h2>New Item</h2>
		<form id="form" action="edititem" method="post">

			<div>
				<label for="itemname" class="red">Item Name *</label> <input id="itemname" type="text" name="itemname" class="form-control" value="<%=request.getParameter("name")%>">
			</div><br>

			<div>
				<label for="type">Type</label> <select style="background-color:white" name="type">
					<option value="0">Goods</option>
					<option value="1">Services</option>
				</select>
			</div><br>

			<div>
				<label for="costprice" class="red">CostPrice *</label> <input name="costprice" type="number" id="cp" value="<%=request.getParameter("cp")%>" class="form-control" min="0" pattern="^\d*(\.\d{0,2})?$">
			</div><br>

			<div>
				<label for="sellingprice" class="red">Selling Price *</label> <input name="sellingprice" type="number" id="sp" class="form-control" value="<%=request.getParameter("sp")%>" min="0" pattern="^\d*(\.\d{0,2})?$">
			</div><br>

			<div>
				<label for="description">Description</label> <textarea name="description" class="form-control"><%=request.getParameter("description")%></textarea>
			</div><br>
			
			<input type="hidden" name="itemId" value="<%=request.getParameter("itemId")%>">
			<button type="button" onclick="checkForm()" class="button">Update</button><br><br>
			<h6 class="red">* Enter either cost price or selling price</h6>
			<br><br>
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