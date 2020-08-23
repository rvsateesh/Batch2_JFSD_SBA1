<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Edit Product(Admin)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<p></p>
<form action="editproduct?action=list" method="POST">
	<div><input type="submit" value="<< back"> </div>
</form>
<hr/>
<% String editid = request.getParameter("pid"); %>
<form action="editproduct?action=editproduct&pid=<%= editid %>" method="POST">
		<div>
			<label>Product Id: </label>
			<input type="number" name="pid" value= "<%= editid %>" ${"readonly" } required/>
			<p></p>
		</div>
		<div>
			<label>Product Name</label>
			<input type="text" name="pname" value="${product.pname }" maxlength="20" required/>
			<p></p>
		</div>
		<div>
			<label>Product Cost</label>
			<input type="number" name="pcost" value="${product.pcost }" min="0" required/>
			<p></p>
		</div>
		<div>
			<label>Product Description</label>
			<input type="text" name="pdesc" value="${product.pdesc }" maxlength="40" />
			<p></p>
		</div>
		
		<button name="editproduct" value="editproduct">SAVE</button>
</form>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>
