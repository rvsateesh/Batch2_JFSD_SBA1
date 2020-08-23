<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalTime" %>
<h5>Disclaimer: Order delivery will take atleast 3 days from today!</h5>
<p></p>
<h5> <%=LocalDate.now() %> <span> | </span> <%=LocalTime.now() %></h5>
