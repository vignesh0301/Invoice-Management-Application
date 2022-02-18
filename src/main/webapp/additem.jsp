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
	

	<div class="container card" style=" padding-top: 50px; padding-left: 50px; padding-right: 50px;">
		
		<h2>New Item</h2>
		<form id="form" action="additem" method="post">

			<div>
				<label for="itemname" class="red">Item Name *</label> <input id="itemname" type="text" name="itemname" class="form-control">
			</div><br>

			<div>
				<label for="type">Type</label> <select style="background-color:white" name="type">
					<option value="0">Goods</option>
					<option value="1">Services</option>
				</select>
			</div><br>

			<div>
				<label for="costprice" class="red">CostPrice *</label> <input name="costprice" type="number" id="cp" class="form-control" min="0" pattern="^\d*(\.\d{0,2})?$">
			</div><br>

			<div>
				<label for="sellingprice" class="red">Selling Price *</label> <input name="sellingprice" type="number" id="sp" min="0" class="form-control" pattern="^\d*(\.\d{0,2})?$">
			</div><br>

			<div>
				<label for="description">Description</label> <textarea  name="description" class="form-control"></textarea>
			</div><br>

			<button type="button" onclick="checkForm()" class="button">Add</button><br><br>
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