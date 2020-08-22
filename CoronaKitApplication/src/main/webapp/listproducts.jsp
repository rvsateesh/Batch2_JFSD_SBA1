<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-All Products(Admin)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<div>
	<form action="newproduct.jsp" method="post">
		<button name="newproduct" value="newproduct">Add New Product</button>
		<p></p>
	</form>
	
</div>
<form action="list?action=list" method="post">
	<c:choose>
		<c:when test="${products == null || products.isEmpty()}"> 
		</c:when>
		<c:otherwise>
				<table border="1" cellspacing="5px" cellpadding="5px">
					<tr>
						<th>Product ID</th>
						<th>Product Name</th>
						<th>Product Cost</th>
						<th>Product Description</th>
						<th>Action</th>
					</tr>
					<c:forEach items="${products }" var="product">
						<tr>
							<td>${product.id }</td>
							<td>${product.productName }</td>
							<td>${product.cost }</td>
							<td>${product.productDescription }</td>
							<td>
								<form action="editproduct.jsp?pid=${product.id }" method="post"> 
									<button name="editproduct" value="editproduct">Edit</button> 
								</form>
								<form action="deleteproduct?action=deleteproduct&pid=${product.id }" method="post">
									<button name="deleteproduct" value="deleteproduct">Delete</button> 
								</form>							
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:otherwise>
	</c:choose>
</form>
<p></p>
	<form action="index.jsp?action=logout" method="post">
		<button name="logout" value="logout">Logout</button>
		<p></p>
	</form>
<hr/>
	<jsp:include page="footer.jsp"/>
</body>
</html>