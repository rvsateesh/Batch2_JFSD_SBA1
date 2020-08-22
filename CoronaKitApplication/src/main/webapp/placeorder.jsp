<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Place Order(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<p></p>
<hr/>
<h3>Congratulations! Order placed and will be delivered on <%= LocalDate.now().plusDays(3) %></h3>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>