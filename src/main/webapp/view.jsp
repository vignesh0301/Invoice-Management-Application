<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Invoice App</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"crossorigin="anonymous">
<link rel="stylesheet" href="./styles.css">

</head>
<body>
<header>
		<ul>
			<li><a class="active" href="<%=request.getContextPath()%>">Invoice App</a></li>
			<li><a href="<%=request.getContextPath()%>/home">Home</a></li>
			<li><a href="<%=request.getContextPath()%>/addcustomer.jsp">New Customer</a></li>
			<li><a href="<%=request.getContextPath()%>/newinvoice">New Invoice</a></li>
			<li><a href="<%=request.getContextPath()%>/additem.jsp">New Item</a></li>
			<li><a class="logout" href="<%=request.getContextPath()%>/logout">Logout</a></li>
		</ul>
	</header>

	<div class="container-fluid">
	    <h5 class="mt-4">Invoice Number: <c:out value="${invoiceNo}"></c:out></h5>
	      <h5 class="mt-4">Customer Name: <c:out value="${name}"></c:out></h5>
	      <h5 class="mt-4">Invoice Date: <c:out value="${date}"></c:out></h5>
	      <h5 class="mt-4">Due Date: <c:out value="${dueDate}"></c:out></h5>
	      <div class="table-responsive table-bordered r-scroll mt-4">
      <table class="table">
        <thead>
          <tr class="my-head bg-primary text-light">
            <th style="text-align:center">Name</th>
            <th style="text-align:center">Price</th>
            <th style="text-align:center">Quantity</th>
            <th style="text-align:center">Amount </th>
          </tr>
        </thead>
        
       <c:forEach var="item" items="${items}">
       <tbody>
         <td style="text-align:center; align-items: center; vertical-align:middle;"><c:out value="${item.name}" /></td>
            <td style="text-align:center; align-items: center; vertical-align:middle;"><c:out value="${item.price}" /></td>
            <td style="text-align:center; align-items: center; vertical-align:middle;"><c:out value="${item.quantity}" /></td>
            <td style="text-align:center; align-items: center; vertical-align:middle;"><c:out value="${item.price*item.quantity}" /></td>
  
       </tbody>
        </c:forEach>
      </table>

      <div class="float-right" style="margin-right:10%; margin-bottom:20px;" >
        <h5 class="mt-4">Amount: ₹ <c:out value="${totalAmount}"></c:out></h5>
          <h5 class="mt-4">Discount: <c:out value="${discount}"></c:out>%</h5>
         <h5 class="mt-4">Total Amount: ₹ <c:out value="${totalAmount*((100-discount)/100)}"></c:out></h5>
    </div>
    </div>

    <div class="container mt-4">
       <c:if test="${paid==false}" var="res"><form id="pay" method="post" action="pay"><label class="mt-2" for="paidDate"><h5>Date of Payment</h5></label><input style="width:170px" id="paidDate" type="date" class="col-xs-2 mt-2 form-control" name="paidDate"><input type="hidden" name="invoiceNo" value="${invoiceNo }"><button type="button" onclick="makePay()" class="mt-4 btn btn-success">Make Payment</button></form></c:if>
       <c:if test="${paid==true }" var="res"><button class="btn btn-success">Paid at  &nbsp; <c:out value="${paidDate }"></c:out></button></c:if>
    </div>
	</div>
	
</body>
<script>
function makePay(){
	if(document.getElementById('paidDate').value==0){
		alert("Please select date of payment");
	} 
	else{
		document.getElementById("pay").submit();
	}
}
</script>
</html>