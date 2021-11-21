<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Invoice App</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
	<link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
		<ul>
			<li><a class="active" href="<%=request.getContextPath()%>">Invoice App</a></li>
			<li><a href="<%=request.getContextPath()%>/home">Home</a></li>
			<li><a href="<%=request.getContextPath()%>/addcustomer.jsp">New Customer</a></li>
			<li><a href="<%=request.getContextPath()%>/newinvoice">New Invoice</a></li>
			<li><a href="<%=request.getContextPath()%>/additem.jsp">New Item</a></li>
			<li><a class="logout"
				href="<%=request.getContextPath()%>/logout">Logout</a></li>
		</ul>
	</header>

  
<div class="container-fluid">
  <h2 class="mt-5 " style="font-family: 'Patua One', cursive;">Items</h2>
  <div class="col-md-12 mt-4">
    <div class="table-responsive r-scroll">
      <table class="table">
        <thead>
          <tr class="my-head bg-primary text-light">
            <th style="text-align:center">Name</th>
            <th style="text-align:center">Cost Price</th>
            <th style="text-align:center">Selling Price</th>
            <th style="text-align:center">Type</th>
            <th style="text-align:center">Delete</th>
          </tr>
        </thead>
        
       <c:forEach var="item" items="${items}">
       <tbody>
         <td style="text-align:center; align-items: center; vertical-align:middle;"><c:out value="${item.name}" /></td>
            <td style="text-align:center; align-items: center; vertical-align:middle;"><c:out value="${item.costPrice}" /></td>
            <td style="text-align:center; align-items: center; vertical-align:middle;"><c:out value="${item.sellingPrice}" /></td>
            <td style="text-align:center; align-items: center; vertical-align:middle;"><c:out value="${item.type}" /></td>
            <td style="text-align:center; align-items: center; vertical-align:middle;"><form action="deleteitem" method="post"><input type="hidden" name="id" value="${item.id}"><button type="submit"  class="btn btn-danger"  >Delete</button></form></td>
           
       </tbody>
        </c:forEach>
      </table>
    
    </div>
     </div>
     </div>
</body>
</html>