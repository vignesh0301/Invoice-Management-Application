<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.customer.CustomerDAO"%>
<%@page import="java.util.List"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Invoice App</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<link rel="stylesheet" href="./styles.css">
</head>

<body>

	<div class="container-fluid">
		<h2 class="mt-4 pt-5" style="font-family: 'Patua One', cursive;">Invoices</h2>

		<div class="col-md-12 mt-3">
			<div class="table-responsive r-scroll">
				<table class="table">
					<thead>
						<tr class="my-head bg-primary text-light">
							<th style="text-align: center">Invoice No</th>
							<th style="text-align: center">Customer Name</th>
							<th style="text-align: center">Date</th>
							<th style="text-align: center">Due Date</th>
					        <th style="text-align: center">Total Amount</th>
							<th style="text-align: center">Status</th>
					    	<th style="text-align: center"></th> 
						</tr>
					</thead>
					<c:forEach var="item" items="${invoices}">
						<tbody>
						
							<td style="text-align: center; align-items: center; vertical-align: middle;"><c:out value="${item.invoiceNo}" /></td>
							<td style="text-align: center; align-items: center; vertical-align: middle;"><c:out value="${item.customerName}" /></td>
							<td style="text-align: center; align-items: center; vertical-align: middle;"><c:out value="${item.date}" /></td>
							<td style="text-align: center; align-items: center; vertical-align: middle;"><c:out value="${item.dueDate}" /></td>
							<td style="text-align: center; align-items: center; vertical-align: middle;"><c:out value="₹${item.totalAmount}" /></td>
							
						    <c:if test="${ item.paidDate== null && item.dueDate>=today}" var="res"><td style="text-align: center; align-items: center; vertical-align: middle;"><c:out value="Pending" /></td></c:if>
					        <c:if test="${ item.paidDate== null && item.dueDate<today}" var="res"><td style="text-align: center; align-items: center; vertical-align: middle;"><c:out value="Overdue" /></td></c:if>
						    <c:if test="${ item.paidDate!= null}" var="res"><td style="text-align: center; align-items: center; vertical-align: middle;"><c:out value="Paid" /></td></c:if>
							<td style="text-align: center; align-items: center; vertical-align: middle;"><form action="invoice" method="get"><input type="hidden" name="invoiceNo" value="${item.invoiceNo}"><button type="submit" class="btn btn-primary">View</button></form></td> 

						</tbody>
					</c:forEach>
				</table>

			</div>
		</div>
	</div>
</body>
</html>