<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Invoice App</title>

<link rel="stylesheet" href="./styles.css">

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>

	
	
	<form class="container-fluid" action="editinvoice" id="invoice" method="post">

		<div class="container">

			<label for="customer">Select Customer</label> 
			<select	name="customerId" class="form-control w-25" id="selectcust">
			 <option value="${customerId}">${name}</option>
				<c:forEach var="cust" items="${customers}">
					<option value="${cust.id}"><c:out value="${ cust.name}" /></option>
				</c:forEach>
			</select><br> 
			
			<label for="customer">Invoice Number</label>
			 <input class="form-control w-25" type="text" name="invoiceNo" value="${invoiceNo }" readonly><br> 
			
			
			<label for="date">Date of Invoice</label>
			 <input class="form-control w-25" id="date" name="date" value="${date }" type="date"> <br>
			 
			 
			  <label for="dueDate">DueDate</label>
			   <input class="form-control w-25" id="dueDate" name="dueDate" value="${dueDate }" type="date"> <br>
			   
			   
			<div class="table-responsive r-scroll">
				<table class="table">
					<thead>
						<tr class="my-head bg-primary text-light">
							<th style="text-align: center">Name</th>
							<th style="text-align: center">Quantity</th>
							<th style="text-align: center">Price</th>
							<th style="text-align: center">Total</th>
							<th style="text-align: center"></th>
						</tr>
					</thead>

					<tbody id="table">
					
					   
					<c:forEach var="item" items="${itemlist}">
					<c:set var="count" value="${count + 1}" scope="page"/>
					<tr>
					<td><input type="hidden" name="id" value="${count}"><input name="itemname${count}" value="${item.name }" type="text" readonly class="form-control"></td>
					<td><input name="quantity${count}" value="${item.quantity }" type="text" readonly class="form-control"></td>
					<td><input name="price${count}" value="${item.price}" type="text" readonly class="form-control"></td>
					<td><input name="total${count}" value="${item.quantity * item.price }" type="text" readonly class="form-control"></td>
					<td><button class="btn btn-danger" type="button" onclick="remove_item(this,${item.quantity * item.price})">Remove</button></td>
					</tr>
					</c:forEach>
					</tbody>
				</table>

			</div><br> 
			<div class="float-right">
			<label for="total">Sub Total</label>
			<h5 class="text-muted " id="total">${Amount}</h5><br>
			<input type="hidden" value="${Amount}" id="amt" name="amt">
			
			<label for="discount">Discount (in %)</label>
			<input id="discount" name="discount" class="form-control" style="width:100px" type="number" value="${discount}"  min="0" value="0" onchange="addDiscount()"><br>
			
			<label for="total">Total Amount</label>
			<h5 class="text-muted " id="totalamt">${totalAmount }</h5><br>
			<button class=" btn btn-primary" type="button" value="${totalAmount}" onclick="checkForm()">Submit</button>
			</div>
		</div>

	</form>

	<form class="container">

		<h2 class="mt-5" style="font-family: 'Patua One', cursive;">Add Items</h2>


		<label for="itemname">Item Name</label> 
		  <select id="itemname" name="itemname" onchange="changePrice()" class="form-control w-25">
			<c:forEach var="item" items="${items}">
				<option value="${item.name}"  data-price="${item.sellingPrice }"><c:out value="${ item.name}" /></option>
			</c:forEach>
		</select> <br> 
		
		<label for="price">Price</label> <input name="price" id="price" type="number" min="0" class="form-control w-25" ><br>
			 
		 <label for="quantity">Quantity</label>	<input id="quantity" name="quantity" type="number" min="1" class="form-control w-25" value="1"><br>

		<button class="btn btn-primary" type="button" onclick="add_item()">Add</button><br><br>
		
	</form>

</body>
<script>


class Invoice{
	 constructor() {
	  }

	  static #count = ${countofitems};

	  static increaseCount() {
	    this.#count += 1;
	  }

	  static getCount() {
	    return this.#count;
	  }
	  
	  static #total = ${Amount}; 
	  static addTotal(amt){
		  this.#total+=amt;
	  }
	  
	  static removeTotal(amt){
		  this.#total-=amt;
	  }
	  
	  static getTotal(){
		  return this.#total;
	  }
	    
}

function add_item(){
	var obj = new Invoice();
	var itemname = document.getElementById("itemname").value; 
	var quantity = document.getElementById("quantity").value; 
	var price = document.getElementById("price").value; 
	
	Invoice.addTotal(parseFloat((quantity*price).toFixed(2)));
	document.getElementById("total").innerHTML=(Invoice.getTotal()).toFixed(2);
	document.getElementById("amt").value=(Invoice.getTotal()).toFixed(2);

	var table = document.getElementById("table");

	  var length = Invoice.getCount()+1;
	  if(quantity>0 && price>0){
	       table.insertRow(-1).innerHTML = '<tr><td><input type="hidden" name="id" value="'+length+'"><input name="itemname'+length+'" type="text" value="'+itemname+'" READONLY class="form-control"></td><td><input READONLY  name="quantity'+length+'" type="text" value="'+quantity+'" class="form-control"></td><td><input READONLY name="price'+length+'" type="text" value="'+price+'" class="form-control"></td><td><input READONLY type="text" value="'+(price*quantity).toFixed(2)+'" class="form-control"></td><td><button class="btn btn-danger" type="button" onclick="remove_item(this,'+(quantity*price).toFixed(2)+')">Remove</button></td></tr>';
	       Invoice.increaseCount();
	       document.getElementById("totalamt").innerHTML = (Invoice.getTotal()*((100-document.getElementById("discount").value)/100)).toFixed(2);
	  }
	
	
	
}
function remove_item(x,a){
	
	Invoice.removeTotal(a);
	document.getElementById("total").innerHTML= (Invoice.getTotal()).toFixed(2);
	document.getElementById("amt").value=(Invoice.getTotal()).toFixed(2);

	
	document.getElementById("totalamt").innerHTML = (Invoice.getTotal()*((100-document.getElementById("discount").value)/100)).toFixed(2);

	document.getElementById("table").deleteRow(x.parentNode.parentNode.rowIndex -1);
	
}
function addDiscount(){
	if(document.getElementById("discount").value>100) document.getElementById("discount").value=100;
	document.getElementById("totalamt").innerHTML = (Invoice.getTotal()*(100-document.getElementById("discount").value)/100).toFixed(2);
	
	
}
function checkForm(){
	
	var d1 = new Date(document.getElementById('date').value); 
	var d2 = new Date(document.getElementById('dueDate').value); 
	var c = d1<=d2;
	
	if( document.getElementById("selectcust").options[document.getElementById("selectcust").selectedIndex].value == "Select Customer")
    {
         alert("Please select a customer");
    }
	else if(document.getElementById('date').value==0 || document.getElementById('dueDate').value==0){
		alert('Please enter date');		
	}
	else if(c==false){
		alert("Make sure that Due Date is after the date of invoice");
	}
	else if(Invoice.getTotal()*((100-document.getElementById("discount").value)/100)<0){
		alert("Please make sure that total amount is greater than or equal to 0");
	}
	else{10.00
		if(document.getElementById('discount').value==0) document.getElementById('discount').value=0;
		document.getElementById('invoice').submit();
	}

}
function changePrice(){
	document.getElementById("price").value=document.getElementById("itemname").options[document.getElementById("itemname").selectedIndex].getAttribute("data-price")

}
</script>
</html>