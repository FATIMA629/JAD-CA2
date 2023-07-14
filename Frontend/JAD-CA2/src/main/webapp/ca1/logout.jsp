<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	// Invalidates the session and clears the data
	session.invalidate();
	// Redirects the user to the login or home page
	response.sendRedirect("home.jsp");
	%>

</body>
</html>