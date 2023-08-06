<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="user.*"%>
<%@ page import="book.*"%>
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
 <jsp:include page="header.jsp"/>

	<%
	if (session != null && session.getAttribute("loggedIn") != null) {
		// User is logged in

		// Check if the user is an admin
		String role = (String) session.getAttribute("role");
		if (!role.equals("admin")) {
			// User is a registered user

			int userId = (int) session.getAttribute("userId");

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
		<form method="post" action="../UserUpdateUserServlet">
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
				<label for="phone" class="form-label">Phone</label> <input
					type="text" class="form-control" id="phone" name="phone"
					value="<%=inputData.getOrDefault("phone", user.getPhone())%>">
				<%
				if (errors != null && errors.containsKey("phone")) {
				%>
				<div class="error"><%=errors.get("phone")%></div>
				<%
				}
				%>
			</div>
			
			<div class="form-group">
					<label for="address1">Address</label>
					<input type="text" name="address" class="form-control" id="address1" value="<%=inputData.getOrDefault("Address", user.getAddress().getAddress1())%>" required>
					<%
				if (errors != null && errors.containsKey("address")) {
				%>
				<div class="error"><%=errors.get("address")%></div>
				<%
				}
				%>
				</div>

				<div class="form-group">
					<label for="address2">Address 2
						<span class="text-muted">(Optional)</span>
					</label>
					<input type="text" name="address2" class="form-control" id="address2" value="<%=inputData.getOrDefault("address2", user.getAddress().getAddress2())%>">
				</div>
				
					<div class="row">
					<div class="col-md-6 form-group">
						<label for="country">Country</label>
						<select type="text" name="country" class="form-control" id="country">
						<%if(user.getAddress().getCountry().equals("United Kingdom")) {%>
						<option><%=user.getAddress().getCountry() %></option>
							<option>Canada</option>
							<option>USA</option>
							<% } else if(user.getAddress().getCountry().equals("Canada")) {%>
							<option><%=user.getAddress().getCountry() %></option>
							<option>United Kingdom</option>
							<option>USA</option>
							<% } else { %>
							<option><%=user.getAddress().getCountry() %></option>
							<option>United Kingdom</option>
							<option>Canada</option>
							<% } %> 
						</select>
					
					</div>

					<div class="col-md-6 form-group">
						<label for="city">City</label>
						<select type="text" name="city" class="form-control" id="city">
						<%if(user.getAddress().getCity().equals("London")) {%>
							<option><%=user.getAddress().getCity() %></option>
							<option>Las Vegas</option>
							<option>Texas</option>
							<% } else if (user.getAddress().getCity().equals("Las Vegas")) { %>
							<option><%=user.getAddress().getCity() %></option>
							<option>London</option>
							<option>Texas</option>
							<%
							} else {
							%>
							<option ><%=user.getAddress().getCity() %></option>
							<option>London</option>
							<option>Las Vegas</option>
							<%
							}
							%>
						</select>
					
					</div>
</div>
				
			<div class="row">
				<div class="col-md-6 form-group">
						<label for="district">District</label>
					<input type="text" name="district" class="form-control" id="district" value="<%=inputData.getOrDefault("district", user.getAddress().getDistrict())%>">
						<%
				if (errors != null && errors.containsKey("district")) {
				%>
				<div class="error"><%=errors.get("district")%></div>
				<%
				}
				%>
					</div>

					<div class="col-md-6 form-group">
						<label for="postalCode">Postal Code</label>
						<input type="text" name="postalCode" class="form-control" id="postalCode" value="<%=inputData.getOrDefault("postalCode", user.getAddress().getPostalCode())%>">
						<%
				if (errors != null && errors.containsKey("postalCode")) {
				%>
				<div class="error"><%=errors.get("postalCode")%></div>
				<%
				}
				%>
					</div>
			</div>

			<div class="mb-3">
				<label for="password" class="form-label">Password:</label> <input
					type="password" class="form-control" id="password" name="password"
					value="<%=inputData.getOrDefault("password", "")%>">
				<%
				if (errors != null && errors.containsKey("password")) {
				%>
				<div class="error"><%=errors.get("password")%></div>
				<%
				}
				%>
			</div>

			<input type="submit" class="btn btn-primary" value="Update User">
		</form>
		
		<hr>
		
	</div>
	
	<%
	} else {
	// User is an admin
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
