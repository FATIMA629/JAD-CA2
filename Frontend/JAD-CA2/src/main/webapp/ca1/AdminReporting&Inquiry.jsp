<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="book.*"%>
<%@ page import="genre.*"%>
<%@ page import="user.*"%>
<%@ page import="order.*"%>
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

#statistics-section {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
}

.statistics-card {
	width: 300px;
	margin: 10px;
	height: 200px;
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
</style>
</head>
<body>
	<jsp:include page="header.jsp" />
	<%
	if (session != null && session.getAttribute("loggedIn") != null) {
		// User is logged in

		// Check if the user is an admin
		String role = (String) session.getAttribute("role");
		if (role.equals("admin")) {
	%>

	<div class="container py-5">

		<h1 class="mb-5">Reporting and Inquiry Dashboard</h1>

		<div class="dashboard-buttons">
			<a href="#statistics-section">Reporting & Inquiry</a> <a
				href="#manage-books-section">Manage Books</a> <a
				href="#manage-users-section">Manage Users</a> <a
				href="#manage-orders-section">Manage Orders</a>
		</div>
		<%
		BookDao bookDao = new BookDao();
		UserDao userDao = new UserDao();
		GenreDao genreDao = new GenreDao();
		%>
		<h3 class="mt-5">Reporting & Inquiry</h3>
		<div id="statistics-section">
			<div class="statistics-card">
				<h3 class="statistics-header">Total Users</h3>
				<p class="statistics-value">
					<%=userDao.getTotalUsers()%>
				</p>
			</div>
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
				<h3 class="statistics-header">Total Revenue</h3>
				<p class="statistics-value">
					$<%=String.format("%.2f", bookDao.getTotalRevenue())%>
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