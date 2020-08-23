<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Order Summary(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<p></p>
<p></p>
<button type="button" name="back" onclick="history.go(-2);">Change Order</button>
<hr/>
<h5>Hello ${coronaKit.personName }</h5>
<p></p>
<h4>Here is your order summary!</h4>
<p></p>
<p>Total price of your order is:<strong> ${coronaKit.totalAmount } </strong></p>
<p>Invoice will be sent to your email-ID:<strong> ${coronaKit.email } </strong></p>
<p>Shipment tracking will be updated to you on contact:<strong> ${coronaKit.contactNumber } </strong></p>
<p>Products will be delivered at your address:<strong> ${coronaKit.deliveryAddress } </strong></p>
<hr/>	
<form action="saveorder?action=saveorder" method = "POST">
	<button name="saveorder" value="saveorder">Place Order >> </button>
</form>
	<jsp:include page="footer.jsp"/>
</body>
</html>
