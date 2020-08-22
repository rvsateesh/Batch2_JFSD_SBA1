<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-All Products(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<p></p>
<button type="button" name="back" onclick="history.back()">Update User Details</button>
<hr/>
<h2>Welcome ${coronaKit.personName }!</h2>
<h3>Select the Quantity of the products you wish to buy</h3>
<div>
<form action="showkit?action=showkit" method="POST">	
	<c:choose>
		<c:when test="${products == null || products.isEmpty()}">
			<h4>Sorry, No products available to shop!</h4>
		</c:when>
		<c:otherwise>
			<table border="1" cellspacing="5px" cellpadding="5px">
				<tr>
					<th>Product ID</th>
					<th>Product Name</th>
					<th>Product Cost</th>
					<th>Product Quantity</th>
				</tr>
				<c:forEach items="${products }" var="product">
				<tr>
					<td>${product.id }</td>
					<td>${product.productName }</td>
					<td>${product.cost }</td>
					<td><input type="number" name="pquantity" value="0" min="0"></td>				
				</tr>
		</c:forEach>				
	</table>
		</c:otherwise>
	</c:choose>
	<p></p>
	<button name="showkit" value="showkit">Got to cart</button>
</form>
</div>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>