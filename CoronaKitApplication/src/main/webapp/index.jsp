<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Home</title>
</head>
<body>
<div>
<h3>Corona Prevention Kit Application</h3>
<hr/>
	<h3>Admin Login</h3>
	<form action="admin?action=login" method="post">
		<div>
			<div><label for="loginid">Enter login Id</label> </div>
			<div><input type="text" id="loginid" name="loginid"> </div>
			<p></p>
		</div>
		<div>
			<div><label for="password">Enter password</label> </div>
			<div><input type="text" id="password" name="password"> </div>
			<p></p>
		</div>
		<div>
			<div><input type="submit" value="Login"> </div>
		</div>
	</form>
</div>
<hr/>
<div>
	<h3>User Portal</h3>
	<a href="newuser.jsp?action=newuser"><button>Create Corona Kit</button></a>
</div>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>
