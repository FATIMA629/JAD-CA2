<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<!-- Customised CSS -->
<link href="css/books.css" rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
.container {
	margin-top: 100px;
}
</style>

</head>
<body>

	<%
	String profileLink = "#";
	String cartLink = "#";
	if (session.getAttribute("userId") != null) {
		profileLink = "profileDetails.jsp";
		cartLink = "cart.jsp";
	} else {
		profileLink = "login.jsp";
		cartLink = "login.jsp";
	}
	%>
	<%
	if (session.getAttribute("successLogin") != null) {
	%>
	alert(
	<%=session.getAttribute("success")%>)
	<%
	}
	%>


 <jsp:include page="header.jsp"/>
	<%if (session == null || session.getAttribute("loggedIn") == null) {
	    // User is not logged in
	    %> 
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-lg-5">
				<h2 class="text-center">Login Form</h2>
				<form action="../VerifyLoginServlet" method="post"
					class="p-4 border rounded-3">
					<div class="mb-3">
						<label for="username" class="form-label">Username:</label> <input
							type="text" id="username" name="username" class="form-control"
							required>
					</div>
					<div class="mb-3">
						<label for="password" class="form-label">Password:</label> <input
							type="password" id="password" name="password"
							class="form-control" required>
					</div>
					<button type="submit" class="btn btn-primary">Login</button>
				</form>
				<p class="text-center mt-3">
					Not a member? <a href="userRegistration.jsp">Click HERE to sign
						up</a>.
				</p>
			</div>
		</div>
	</div>
    <% 
} else {
    // User is logged in
    response.sendRedirect("home.jsp"); // Redirect to the home page
}
%>

	<%-- Display error messages --%>
	<%
	String errorCode = request.getParameter("errCode");
	if (errorCode != null) {
		if (errorCode.equals("invalidLogin")) {
	%>
	<div class="alert alert-danger" role="alert">Username or Password
		is incorrect, please try again</div>

	<%
	}
	}
	%>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>
</body>
</html>
