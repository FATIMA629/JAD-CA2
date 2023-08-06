<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="book.*"%>
<%@ page import="user.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit User</title>
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

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>

<style>
.error {
	color: red;
}
</style>
</head>
<body>
	<%-- Navigation Bar --%>
	<jsp:include page="header.jsp" />

	<%
	if (session != null && session.getAttribute("loggedIn") != null) {
		// User is logged in

		// Check if the user is an admin
		String role = (String) session.getAttribute("role");
		if (role.equals("admin")) {
			String userId = request.getParameter("id");
			UserDao userDao = new UserDao();
			User user = userDao.getUserById(userId);
			Map<String, String> errors = (Map<String, String>) session.getAttribute("errors");
			Map<String, String> inputData = (Map<String, String>) session.getAttribute("inputData");
			if (inputData == null) {
		inputData = new HashMap<>();
			}
	%>

	<%-- Edit User Form --%>
	<div class="container mt-5">
		<h2 class="mb-4">Edit User</h2>
		<form method="post" action="../UpdateUserServlet">
			<input type="hidden" name="userId" value="<%=user.getUserID()%>">

			<div class="mb-3">
				<label for="username" class="form-label">Username:</label> <input
					type="text" class="form-control" id="username" name="username"
					value="<%=inputData.getOrDefault("username", user.getUserName())%>">
				<%
				if (errors != null && errors.containsKey("username")) {
				%>
				<div class="error"><%=errors.get("username")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="email" class="form-label">Email:</label> <input
					type="email" class="form-control" id="email" name="email"
					value="<%=inputData.getOrDefault("email", user.getEmail())%>">
				<%
				if (errors != null && errors.containsKey("email")) {
				%>
				<div class="error"><%=errors.get("email")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="role" class="form-label">Role:</label> <select
					class="form-control" id="role" name="role">
					<option value="admin"
						<%=user.getRole().equals("admin") ? "selected" : ""%>>Admin</option>
					<option value="user"
						<%=user.getRole().equals("user") ? "selected" : ""%>>User</option>
				</select>
				<%
				if (errors != null && errors.containsKey("role")) {
				%>
				<div class="error"><%=errors.get("role")%></div>
				<%
				}
				%>
			</div>

			<%-- Shipping Address fields --%>
			<h3>Shipping Address:</h3>
			<div class="mb-3">
				<label for="country" class="form-label">Country:</label> <input
					type="text" class="form-control" id="country" name="country"
					value="<%=user.getAddress().getCountry()%>">
				<%
				if (errors != null && errors.containsKey("country")) {
				%>
				<div class="error"><%=errors.get("country")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="address1" class="form-label">Address1:</label> <input
					type="text" class="form-control" id="address1" name="address1"
					value="<%=user.getAddress().getAddress1()%>">
				<%
				if (errors != null && errors.containsKey("address1")) {
				%>
				<div class="error"><%=errors.get("address1")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="address2" class="form-label">Address2:</label> <input
					type="text" class="form-control" id="address2" name="address2"
					value="<%=user.getAddress().getAddress2()%>">
				<%
				if (errors != null && errors.containsKey("address2")) {
				%>
				<div class="error"><%=errors.get("address2")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="district" class="form-label">District:</label> <input
					type="text" class="form-control" id="district" name="district"
					value="<%=user.getAddress().getDistrict()%>">
				<%
				if (errors != null && errors.containsKey("district")) {
				%>
				<div class="error"><%=errors.get("district")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="city" class="form-label">City:</label> <input
					type="text" class="form-control" id="city" name="city"
					value="<%=user.getAddress().getCity()%>">
				<%
				if (errors != null && errors.containsKey("city")) {
				%>
				<div class="error"><%=errors.get("city")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="postalCode" class="form-label">Postal Code:</label> <input
					type="text" class="form-control" id="postalCode" name="postalCode"
					value="<%=user.getAddress().getPostalCode()%>">
				<%
				if (errors != null && errors.containsKey("postalCode")) {
				%>
				<div class="error"><%=errors.get("postalCode")%></div>
				<%
				}
				%>
			</div>

			<input type="submit" class="btn btn-primary" value="Update User">
		</form>
	</div>
	<%
	} else {
	// User is not an admin
	response.sendRedirect("home.jsp"); // Redirect to the home page
	}
	} else {
	// User is not logged in
	response.sendRedirect("home.jsp"); // Redirect to the home page
	}
	%>


	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>
</body>
</html>
