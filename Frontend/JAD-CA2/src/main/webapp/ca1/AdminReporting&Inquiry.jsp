<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="book.*"%>
<%@ page import="genre.*"%>
<%@ page import="user.*"%>
<%@ page import="order.*"%>
<%@ page import="sales.*"%>
<%@ page import="java.util.*"%>
<%@ page import="order.Order"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reporting and Inquiry Dashboard</title>

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
/* Additional styles for order management section */
.order-table th, .order-table td {
	vertical-align: middle;
}

.col-select-all {
	width: 50px;
}

.col-order-id {
	width: 100px;
}

.col-user-id {
	width: 100px;
}

.col-total-price {
	width: 100px;
}

.col-order-date {
	width: 150px;
}

.col-order-status {
	width: 150px;
}

.col-shipping-address {
	width: 200px;
}

.col-order-items {
	width: 250px;
}

.col-update {
	width: 80px;
}

.order-checkbox {
	margin: 0 auto;
	display: block;
}

/* Delete button styling */
#delete-selected-button {
	margin-top: 20px;
}

/* Adjust table styles */
.table {
	width: 100%;
	max-width: 100%;
	margin-bottom: 1rem;
	background-color: transparent;
}

.error {
	color: red;
}

.book-table {
	table-layout: fixed;
	word-wrap: break-word;
}

.book-table .col-book-id {
	width: 80px;
}

.col-book-id {
	width: 80px;
}

.col-title {
	width: 200px;
}

.col-author {
	width: 150px;
}

.col-genre-id {
	width: 80px;
}

.col-price {
	width: 80px;
}

.col-quantity {
	width: 100px;
}

.col-publisher {
	width: 150px;
}

.col-publishDate {
	width: 150px;
}

.col-isbn {
	width: 150px;
}

.col-rating {
	width: 80px;
}

.col-description {
	width: 400px;
}

.col-image-loc {
	width: 600px;
}

.col-sold {
	width: 80px;
}

.col-update {
	width: 80px;
}

.col-delete {
	width: 80px;
}

.dashboard-buttons {
	display: flex;
	justify-content: center;
	gap: 10px;
	margin-bottom: 20px;
}

.dashboard-buttons a {
	display: inline-block;
	padding: 10px 20px;
	background-color: #007bff;
	color: #fff;
	text-decoration: none;
	border-radius: 5px;
	transition: background-color 0.3s ease;
}

.dashboard-buttons a:hover {
	background-color: #0056b3;
}

#books-section, #users-section, #sales-section {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
}

.statistics-card {
	width: 300px;
	margin: 10px;
	height: auto;
	padding: 20px;
	text-align: center;
	background-color: #f8f8f8;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	margin-bottom: 20px;
	padding: 20px;
	border: 1px solid #ddd;
	border-radius: 5px;
	justify-content: center;
	align-items: center;
}

.statistics-header {
	font-size: 24px;
	font-weight: bold;
	justify-content: center;
	align-items: center;
}

.statistics-value {
	font-size: 20px;
	font-weight: bold;
	justify-content: center;
	align-items: center;
}

.statistics-card h3 {
	font-size: 18px;
	margin-bottom: 10px;
	justify-content: center;
	align-items: center;
}

.statistics-card p {
	font-size: 24px;
	font-weight: bold;
	justify-content: center;
	align-items: center;
}

.statistics-card ul {
	list-style: none;
	padding: 0;
	margin: 10px 0;
	justify-content: center;
	align-items: center;
}

.statistics-card ul li {
	margin-bottom: 5px;
	justify-content: center;
	align-items: center;
}

.statistics-card a {
	text-decoration: none;
	color: #007bff;
	justify-content: center;
	align-items: center;
}

.statistics-card a:hover {
	text-decoration: underline;
	justify-content: center;
	align-items: center;
}

.scrollable-list {
	max-height: 150px; /* changed from 300px to 150px */
	overflow-y: auto;
}
</style>

<script>
	window.onload = function() {
		document.getElementById("endDate").onchange = function() {
			if (this.value < document.getElementById("startDate").value) {
				alert("End date cannot be before start date!");
				this.value = document.getElementById("startDate").value;
			}
		};
	};
</script>

</head>
<body>
	<jsp:include page="header.jsp" />
	<%
	if (session != null && session.getAttribute("loggedIn") != null) {
		// User is logged in

		// Check if the user is an admin
		String role = (String) session.getAttribute("role");
		if (role.equals("admin")) {

			BookDao bookDao = new BookDao();
			UserDao userDao = new UserDao();
			GenreDao genreDao = new GenreDao();
	%>



	<div class="container py-5">

		<h1 class="mb-5">Reporting and Inquiry Dashboard</h1>

		<div class="dashboard-buttons">
			<a href="#books-section">Books Report</a> <a href="#users-section">Users
				Report</a> <a href="#sales-section">Sales Report</a>
		</div>

		<h3 class="mt-5">Books Report</h3>
		<div id="books-section">
			<!-- Books Report Section -->
			<div class="statistics-card">
				<h3 class="statistics-header">Total Type Of Books</h3>
				<p class="statistics-value">
					<%=bookDao.getTotalTypeOfBooks()%>
				</p>
			</div>
			<div class="statistics-card">
				<h3 class="statistics-header">Total Number Of Books</h3>
				<p class="statistics-value">
					<%=bookDao.getTotalBooks()%>
				</p>
			</div>
			<div class="statistics-card">
				<h3 class="statistics-header">Total Number of Books Sold</h3>
				<p class="statistics-value">
					<%=bookDao.getTotalBooksSold()%>
				</p>
			</div>
			<div class="statistics-card">
				<h3 class="statistics-header">Average Rating of All Books</h3>
				<p class="statistics-value">
					<%=String.format("%.1f", (float) bookDao.getAverageRatingOfAllBooks())%>
					stars
				</p>
			</div>

			<div class="statistics-card">
				<h3 class="statistics-header">Books Low Instock</h3>
				<ul>
					<%
					List<Book> lowestStock = bookDao.getLowestStockBooks(3);
					for (Book book : lowestStock) {
					%>
					<li><%=book.getTitle()%></li>
					<%
					}
					%>
				</ul>
			</div>
			<div class="statistics-card">
				<h3 class="statistics-header">Newest Books</h3>
				<ul>
					<%
					List<Book> newestBooks = bookDao.getNewestBooks(3);
					for (Book book : newestBooks) {
					%>
					<li><%=book.getTitle()%></li>
					<%
					}
					%>
				</ul>
			</div>
			<div class="statistics-card">
				<h3 class="statistics-header">Highest Rated Books</h3>
				<ul>
					<%
					List<Book> highestRatedBooks = bookDao.getHighestRatedBooks(3);
					for (Book book : highestRatedBooks) {
					%>
					<li><%=book.getTitle()%> - <%=book.getRating()%> stars</li>
					<%
					}
					%>
				</ul>
			</div>
			<div class="statistics-card">
				<h3 class="statistics-header">Popular Genres</h3>
				<ul>
					<%
					Map<Genre, Integer> popularGenres = genreDao.getPopularGenres(3);
					for (Map.Entry<Genre, Integer> entry : popularGenres.entrySet()) {
						Genre genre = entry.getKey();
						int genreCount = entry.getValue();
					%>
					<li><%=genre.getGenreName()%> - <%=genreCount%> books</li>
					<%
					}
					%>
				</ul>
			</div>
			<div class="statistics-card">
				<h3 class="statistics-header">Top Rated Genres</h3>
				<ul>
					<%
					List<Genre> topRatedGenres = genreDao.getTopRatedGenres(3);
					for (Genre genre : topRatedGenres) {
					%>
					<li><%=genre.getGenreName()%></li>
					<%
					}
					%>
				</ul>
			</div>
			<div class="statistics-card">
				<h3 class="statistics-header">Top Selling Books</h3>
				<ul>
					<%
					List<Book> topSellingBooks = bookDao.getTopSellingBooks(3);
					for (Book book : topSellingBooks) {
					%>
					<li><%=book.getTitle()%> - <%=book.getSold()%> copies sold</li>
					<%
					}
					%>
				</ul>
			</div>
			<div class="statistics-card">
				<h3 class="statistics-header">Worst Selling Books</h3>
				<ul>
					<%
					List<Book> worstSellingBoooks = bookDao.getWorstSellingBooks(3);
					for (Book book : worstSellingBoooks) {
					%>
					<li><%=book.getTitle()%> - <%=book.getSold()%> copies sold</li>
					<%
					}
					%>
				</ul>
			</div>
		</div>

		<h3 class="mt-5">Users Report</h3>
		<div id="users-section">
			<!-- Users Report Section -->
			<form action="../UserReportingServlet" method="POST"
				class="form-group">
				<h3 class="mb-3">Filter Options</h3>
				<div class="row">
					<div class="col">
						<label for="userRole">Users by Role</label> <select
							class="form-control" id="userRole" name="userRole">
							<option value="">--Select Role--</option>
							<option value="admin">Admin</option>
							<option value="member">Member</option>
							<!-- Add more options as per your role list -->
						</select>
					</div>
					<div class="col">
						<label for="userIdSpending">User Spending</label> <input
							type="number" class="form-control" id="userIdSpending"
							name="userIdSpending" placeholder="Enter User ID">
					</div>
					<div class="col">
						<label for="userAddressCriteria">Users by Address Criteria</label>
						<select class="form-control" id="userAddressCriteria"
							name="userAddressCriteria">
							<option value="">--Select Criteria--</option>
							<option value="address">Address</option>
							<option value="city">City</option>
							<option value="country">Country</option>
							<option value="district">District</option>
							<option value="postal_code">Postal Code</option>
							<option value="address2">Address2</option>
						</select>
					</div>
				</div>

				<div class="row mt-3">
					<div class="col">
						<label for="userAddressInput">Address Input:</label> <input
							type="text" class="form-control" id="userAddressInput"
							name="userAddressInput" placeholder="Enter Search Term">
					</div>
				</div>

				<div class="mt-3">
					<input type="submit" value="Submit" class="btn btn-primary">
				</div>
			</form>


			<div id="users-section">

				<div class="statistics-card">
					<h3 class="statistics-header">Total Number of Users</h3>
					<p class="statistics-value">
						<%=userDao.getTotalUsers()%>
					</p>
				</div>

				<div class="statistics-card">
					<h3 class="statistics-header">User Details Based on Role</h3>
					<div class="scrollable-list">
						<ul>
							<%
							List<User> usersByRole = (List<User>) session.getAttribute("usersByRole");
							if (usersByRole != null) {
								for (User user : usersByRole) {
							%>
							<li><%=user.getUserName()%></li>
							<%
							}
							} else {
							%>
							<li>No users found.</li>
							<%
							}
							%>
						</ul>
					</div>
				</div>

				<div class="statistics-card">
					<h3 class="statistics-header">User Expenditure</h3>
					<li class="statistics-value">
						<%
						Double userSpending = (Double) session.getAttribute("userSpending");
						if (userSpending != null) {
						%> $<%=String.format("%.2f", userSpending)%> <%
 } else {
 %> No user spending data found. <%
 }
 %>
					</li>
				</div>

				<div class="statistics-card">
					<h3 class="statistics-header">Users Based on Address Criteria</h3>
					<div class="scrollable-list">
						<ul>
							<%
							List<User> usersByAddress = (List<User>) session.getAttribute("usersByAddress");
							if (usersByAddress != null) {
								for (User user : usersByAddress) {
							%>
							<li><%=user.getUserName()%></li>
							<%
							}
							} else {
							%>
							<li>No users found.</li>
							<%
							}
							%>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<h3 class="mt-5">Sales Report</h3>
		<div id="sales-section">

			<form method="POST" class="form-group" action="../ReportingServlet">
				<h3 class="mb-3">Filter Options</h3>
				<div class="row">
					<div class="col">
						<label for="numTopSellingBooks">Number of Top Selling
							Books:</label> <input type="number" class="form-control"
							id="numTopSellingBooks" name="numTopSellingBooks" placeholder="5">
					</div>
					<div class="col">
						<label for="numTopOrders">Number of Top Orders:</label> <input
							type="number" class="form-control" id="numTopOrders"
							name="numTopOrders" placeholder="5">
					</div>
					<div class="col">
						<label for="numTopCustomers">Number of Top Customers:</label> <input
							type="number" class="form-control" id="numTopCustomers"
							name="numTopCustomers" placeholder="5">
					</div>
				</div>

				<div class="row mt-3">
					<div class="col">
						<label for="targetDate">Date:</label> <input type="date"
							class="form-control" id="targetDate" name="targetDate">
					</div>
					<div class="col">
						<label for="startDate">Start Date:</label> <input type="date"
							class="form-control" id="startDate" name="startDate">
					</div>
					<div class="col">
						<label for="endDate">End Date:</label> <input type="date"
							class="form-control" id="endDate" name="endDate">
					</div>
				</div>

				<div class="row mt-3">
					<div class="col">
						<label for="targetGenreId">Genre ID:</label> <input type="number"
							class="form-control" id="targetGenreId" name="targetGenreId"
							placeholder="1">
					</div>
				</div>

				<div class="mt-3">
					<input type="submit" value="Submit" class="btn btn-primary">
				</div>
			</form>


			<div id="sales-section">
			
				<!-- Total Revenue Section -->
				<div class="statistics-card">
					<h3 class="statistics-header">Total Revenue</h3>
					<p class="statistics-value">
						<%
						if (session.getAttribute("totalRevenue") != null) {
						%>
						$<%=String.format("%.2f", (Double) session.getAttribute("totalRevenue"))%>
						<%
						}
						%>
					</p>
				</div>

				<!-- Top Selling Books Section -->
				<div class="statistics-card">
					<h3 class="statistics-header">Top Selling Books</h3>
					<ul>
						<%
						List<Book> topSellingBooks2 = (List<Book>) session.getAttribute("topSellingBooks");
						if (topSellingBooks2 != null && !topSellingBooks2.isEmpty()) {
							for (Book book : topSellingBooks2) {
						%>
						<li><%=book.getTitle()%> - <%=book.getSold()%> copies sold</li>
						<%
						}
						} else {
						String topSellingBooksError = (String) session.getAttribute("topSellingBooksError");
						if (topSellingBooksError != null && !topSellingBooksError.trim().isEmpty()) {
						%>
						<li class="error-message"><%=topSellingBooksError%></li>
						<%
						session.removeAttribute("topSellingBooksError");
						}
						}
						%>
					</ul>
				</div>

				<!-- Top Orders Section -->
				<div class="statistics-card">
					<h3 class="statistics-header">Top Orders</h3>
					<ul>
						<%
						List<Order> topOrders = (List<Order>) session.getAttribute("topOrders");
						if (topOrders != null && !topOrders.isEmpty()) {
							for (Order order : topOrders) {
						%>
						<li>Order ID: <%=order.getOrderId()%>, Total Price: $<%=order.getTotalPrice()%></li>
						<%
						}
						} else {
						String topOrdersError = (String) session.getAttribute("topOrdersError");
						if (topOrdersError != null && !topOrdersError.trim().isEmpty()) {
						%>
						<li class="error-message"><%=topOrdersError%></li>
						<%
						session.removeAttribute("topOrdersError");
						}
						}
						%>
					</ul>
				</div>

				<!-- Top Customers Section -->
				<div class="statistics-card">
					<h3 class="statistics-header">Top Customers</h3>
					<ul>
						<%
						List<User> topCustomers = (List<User>) session.getAttribute("topCustomers");
						if (topCustomers != null && !topCustomers.isEmpty()) {
							for (User customer : topCustomers) {
						%>
						<li><%=customer.getUserName()%> - Total Spending: $<%=String.format("%.2f", userDao.getTotalSpendingByUserId(customer.getUserID()))%></li>
						<%
						}
						} else {
						String topCustomersError = (String) session.getAttribute("topCustomersError");
						if (topCustomersError != null && !topCustomersError.trim().isEmpty()) {
						%>
						<li class="error-message"><%=topCustomersError%></li>
						<%
						session.removeAttribute("topCustomersError");
						}
						}
						%>
					</ul>
				</div>

				<!-- Book Sales by Date Section -->
				<div class="statistics-card">
					<h3 class="statistics-header">Book Sales by Date</h3>
					<ul>
						<%
						List<Book> bookSalesByDate = (List<Book>) session.getAttribute("bookSalesByDate");
						if (bookSalesByDate != null && !bookSalesByDate.isEmpty()) {
							for (Book book : bookSalesByDate) {
						%>
						<li><%=book.getTitle()%> - Quantity Sold: <%=book.getSold()%></li>
						<%
						}
						} else {
						String bookSalesByDateError = (String) session.getAttribute("bookSalesByDateError");
						if (bookSalesByDateError != null && !bookSalesByDateError.trim().isEmpty()) {
						%>
						<li class="error-message"><%=bookSalesByDateError%></li>
						<%
						session.removeAttribute("bookSalesByDateError");
						}
						}
						%>
					</ul>
				</div>

				<!-- Book Sales by Period Section -->
				<div class="statistics-card">
					<h3 class="statistics-header">Book Sales by Period</h3>
					<ul>
						<%
						List<Book> bookSalesByPeriod = (List<Book>) session.getAttribute("bookSalesByPeriod");
						if (bookSalesByPeriod != null && !bookSalesByPeriod.isEmpty()) {
							for (Book book : bookSalesByPeriod) {
						%>
						<li><%=book.getTitle()%> - Quantity Sold: <%=book.getSold()%></li>
						<%
						}
						} else {
						String bookSalesByPeriodError = (String) session.getAttribute("bookSalesByPeriodError");
						if (bookSalesByPeriodError != null && !bookSalesByPeriodError.trim().isEmpty()) {
						%>
						<li class="error-message"><%=bookSalesByPeriodError%></li>
						<%
						session.removeAttribute("bookSalesByPeriodError");
						}
						}
						%>
					</ul>
				</div>

				<!-- Book Sales by Genre Section -->
				<div class="statistics-card">
					<h3 class="statistics-header">Book Sales by Genre</h3>
					<ul>
						<%
						List<Book> bookSalesByGenre = (List<Book>) session.getAttribute("bookSalesByGenre");
						if (bookSalesByGenre != null && !bookSalesByGenre.isEmpty()) {
							for (Book book : bookSalesByGenre) {
						%>
						<li><%=book.getTitle()%> - Quantity Sold: <%=book.getSold()%></li>
						<%
						}
						} else {
						String bookSalesByGenreError = (String) session.getAttribute("bookSalesByGenreError");
						if (bookSalesByGenreError != null && !bookSalesByGenreError.trim().isEmpty()) {
						%>
						<li class="error-message"><%=bookSalesByGenreError%></li>
						<%
						session.removeAttribute("bookSalesByGenreError");
						}
						}
						%>
					</ul>
				</div>

			</div>
		</div>

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

</body>
</html>