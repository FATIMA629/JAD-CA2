<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="order.*"%>
<%@ page import="java.util.HashMap, java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Order</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<link href="css/orders.css" rel="stylesheet" />
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
	<jsp:include page="header.jsp" />
	<%
		if (session != null && session.getAttribute("loggedIn") != null) {
	// User is logged in
	String role = (String) session.getAttribute("role");
			if (role.equals("admin")) {
		String orderId = request.getParameter("id");
	OrderDao orderDao = new OrderDao();
	Order order = orderDao.getOrderById(Integer.parseInt(orderId));
	Map<String, String> errors = (Map<String, String>) session.getAttribute("errors");
	Map<String, String> inputData = (Map<String, String>) session.getAttribute("inputData");
	if (inputData == null) {
		inputData = new HashMap<>();
	}
	%>

	<div class="container mt-5">
		<h2 class="mb-4">Update Order</h2>
		<form method="post" action="../UpdateOrderServlet">
			<input type="hidden" name="orderId" value="<%=order.getOrderId()%>">

			<!-- User ID field -->
			<div class="mb-3">
				<label for="userId" class="form-label">User ID:</label> <input
					type="text" class="form-control" id="userId" name="userId"
					value="<%=order.getUserId()%>">
				<%
				if (errors != null && errors.containsKey("userId")) {
				%>
				<div class="error"><%=errors.get("userId")%></div>
				<%
				}
				%>
			</div>

			<!-- Total Price field -->
			<div class="mb-3">
				<label for="totalPrice" class="form-label">Total Price:</label> <input
					type="text" class="form-control" id="totalPrice" name="totalPrice"
					value="<%=order.getTotalPrice()%>">
				<%
				if (errors != null && errors.containsKey("totalPrice")) {
				%>
				<div class="error"><%=errors.get("totalPrice")%></div>
				<%
				}
				%>
			</div>

			<!-- Order Date field -->
			<div class="mb-3">
				<label for="orderDate" class="form-label">Order Date:</label> <input
					type="text" class="form-control" id="orderDate" name="orderDate"
					value="<%=order.getOrderDate()%>">
				<%
				if (errors != null && errors.containsKey("orderDate")) {
				%>
				<div class="error"><%=errors.get("orderDate")%></div>
				<%
				}
				%>
			</div>

			<!-- Order Status field -->
			<div class="mb-3">
				<label for="orderStatus" class="form-label">Order Status:</label> <select
					class="form-control" id="orderStatus" name="orderStatus">
					<option value="Shipped"
						<%=order.getOrderStatus().equals("Shipped") ? "selected" : ""%>>Shipped</option>
					<option value="Processing"
						<%=order.getOrderStatus().equals("Processing") ? "selected" : ""%>>Processing</option>
					<option value="Cancelled"
						<%=order.getOrderStatus().equals("Cancelled") ? "selected" : ""%>>Cancelled</option>
					<option value="Delivered"
						<%=order.getOrderStatus().equals("Delivered") ? "selected" : ""%>>Delivered</option>
				</select>
				<%
				if (errors != null && errors.containsKey("orderStatus")) {
				%>
				<div class="error"><%=errors.get("orderStatus")%></div>
				<%
				}
				%>
			</div>


			<%-- Shipping Address fields --%>
			<h3>Shipping Address:</h3>
			<div class="mb-3">
				<label for="country" class="form-label">Country:</label> <input
					type="text" class="form-control" id="country" name="country"
					value="<%=order.getShippingAddress().getCountry()%>">
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
					value="<%=order.getShippingAddress().getAddress1()%>">
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
					value="<%=order.getShippingAddress().getAddress2()%>">
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
					value="<%=order.getShippingAddress().getDistrict()%>">
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
					value="<%=order.getShippingAddress().getCity()%>">
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
					value="<%=order.getShippingAddress().getPostalCode()%>">
				<%
				if (errors != null && errors.containsKey("postalCode")) {
				%>
				<div class="error"><%=errors.get("postalCode")%></div>
				<%
				}
				%>
			</div>

			<%-- Order Items fields --%>
			<h3>Order Items:</h3>
			<%
			for (OrderItem item : order.getOrderItems()) {
				int itemId = item.getOrderItemId(); // Assuming you have a method to get the unique ID
			%>
			<div class="mb-3">
				<label for="bookId_<%=itemId%>" class="form-label">Book ID:</label>
				<input type="text" class="form-control" id="bookId_<%=itemId%>"
					name="bookId_<%=itemId%>" value="<%=item.getBook().getBookId()%>">
				<%
				if (errors != null && errors.containsKey("bookId_" + itemId)) {
				%>
				<div class="error"><%=errors.get("bookId_" + itemId)%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="quantity_<%=itemId%>" class="form-label">Quantity:</label>
				<input type="text" class="form-control" id="quantity_<%=itemId%>"
					name="quantity_<%=itemId%>" value="<%=item.getQuantity()%>">
				<%
				if (errors != null && errors.containsKey("quantity_" + itemId)) {
				%>
				<div class="error"><%=errors.get("quantity_" + itemId)%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="unitPrice_<%=itemId%>" class="form-label">Unit
					Price:</label> <input type="text" class="form-control"
					id="unitPrice_<%=itemId%>" name="unitPrice_<%=itemId%>"
					value="<%=item.getUnitPrice()%>">
				<%
				if (errors != null && errors.containsKey("unitPrice_" + itemId)) {
				%>
				<div class="error"><%=errors.get("unitPrice_" + itemId)%></div>
				<%
				}
				%>
			</div>
			<%
			}
			%>



			<!-- Submit button -->
			<input type="submit" class="btn btn-primary" value="Update Order">
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

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>

	<%-- Display success message if present --%>
	<%
	String successMessage = (String) session.getAttribute("success");
	if (successMessage != null && !successMessage.isEmpty()) {
	%>
	<div class="alert alert-success">
		<%=successMessage%>
	</div>
	<%
	}
	session.removeAttribute("success"); // Remove the success attribute from the session
	%>

</body>
</html>
