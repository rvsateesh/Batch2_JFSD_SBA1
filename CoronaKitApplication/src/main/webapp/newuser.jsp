<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-New User(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>

<form action="insertuser?action=insertuser" method="POST">
	<div>
		<label>Name</label> <input type="text" name="Name" maxlength="20" required/>
		<p></p>
		<label>Email</label> <input type="email" name="Email"required/>
		<p></p>
		<label>Contact</label> <input type="text" name="Contact" maxlength="10" required/>		
		<p></p>
		<label>Address</label> <input type="text" name="Address" required/>		
		<p></p>
		<button name="insertuser" value="insertuser">Go to Products</button>
	</div>
</form>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>
