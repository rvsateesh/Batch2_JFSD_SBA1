<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-My Kit(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<h4>Here is your corona prevention kit!</h4>
<p></p>
<button type="button" name="back" onclick="history.back()">Moidfy Kit</button>
<form action="ordersummary?action=ordersummary" method="POST">
<c:choose>
	<c:when test="${products == null || products.isEmpty()}">
	</c:when>
	<c:otherwise>
		<table border="1" cellspacing="5px" cellpadding="5px">
			<tr>
				<th>Product Name</th>
				<th>Product Cost</th>
				<th>Product Quantity</th>
				<th>Product Price</th>
			</tr>
			<c:forEach items="${products }" var="product">
			<tr>
				<td>${product.productName }</td>
				<td>${product.cost }</td>
				<td>${product.productDescription }</td>
				<td>${product.productDescription * product.cost }</td>
			</tr>
			</c:forEach>
		</table>
	</c:otherwise>
</c:choose>	
<p></p>
<button name="ordersummary" value="ordersummary">Order Summary</button>
</form>	
	<jsp:include page="footer.jsp"/>
</body>
</html>