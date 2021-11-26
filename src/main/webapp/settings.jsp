<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Invoice App</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"crossorigin="anonymous">
<link rel="stylesheet" href="./styles.css">

</head>
<body>
<div class="container mt-2">
	<h5 class="text-warning">Warning: Deleting your account cannot be reversed</h5>
	<form method="post" action="deleteuser">
	     <input type="password" name="password" class="form-control w-25" placeholder="Enter your password">
	     <button type="submit" class="btn btn-danger">Delete My Account</button>
	</form>
	</div>
</body>
</html>