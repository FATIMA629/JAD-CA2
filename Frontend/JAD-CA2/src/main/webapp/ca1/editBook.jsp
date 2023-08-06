<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="book.*"%>
<%@ page import="genre.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.HashMap, java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Book</title>

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
		if (role.equals("admin")) {
			String bookId = request.getParameter("id");
			BookDao bookDao = new BookDao();
			GenreDao genreDao = new GenreDao();
			Book book = bookDao.getBookById(Integer.parseInt(bookId));
			Map<String, String> errors = (Map<String, String>) session.getAttribute("errors");
			Map<String, String> inputData = (Map<String, String>) session.getAttribute("inputData");
			if (inputData == null) {
		inputData = new HashMap<>();
			}
	%>

	<div class="container mt-5">
		<h2 class="mb-4">Edit Book</h2>
		<form method="post" action="../UpdateBookServlet" enctype="multipart/form-data">
			<input type="hidden" name="bookId" value="<%=book.getBookId()%>">

			<div class="mb-3">
				<label for="title" class="form-label">Title:</label> <input
					type="text" class="form-control" id="title" name="title"
					value="<%=inputData.getOrDefault("title", book.getTitle())%>">
				<%
				if (errors != null && errors.containsKey("title")) {
				%>
				<div class="error"><%=errors.get("title")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="author" class="form-label">Author:</label> <input
					type="text" class="form-control" id="author" name="author"
					value="<%=inputData.getOrDefault("author", book.getAuthor())%>">
				<%
				if (errors != null && errors.containsKey("author")) {
				%>
				<div class="error"><%=errors.get("author")%></div>
				<%
				}
				%>
			</div>


			<div class="mb-3">
				<label for="genreId" class="form-label">Genre</label> <select
					class="form-control" id="genreId" name="genreId">
					<%
					List<Genre> genres = genreDao.getAllGenres();
					for (Genre genre : genres) {
						String selected = genre.getGenreId() == book.getGenreId() ? "selected" : "";
						System.out.println(selected);
					%>
					<option value="<%=genre.getGenreId()%>" <%=selected%>><%=genre.getGenreName()%></option>
					<%
					}
					%>
				</select>
				<%
				if (errors != null && errors.containsKey("genreId")) {
					out.println("<div class='error'>" + errors.get("genreId") + "</div>");
				}
				%>
			</div>


			<div class="mb-3">
				<label for="price" class="form-label">Price:</label> <input min="0"
					type="number" class="form-control" id="price" name="price"
					value="<%=inputData.getOrDefault("price", String.valueOf(book.getPrice()))%>">
				<%
				if (errors != null && errors.containsKey("price")) {
				%>
				<div class="error"><%=errors.get("price")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="quantity" class="form-label">Quantity:</label> <input
					min="0" type="number" class="form-control" id="quantity"
					name="quantity"
					value="<%=inputData.getOrDefault("quantity", String.valueOf(book.getQuantity()))%>">
				<%
				if (errors != null && errors.containsKey("quantity")) {
				%>
				<div class="error"><%=errors.get("quantity")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="publisher" class="form-label">Publisher:</label> <input
					type="text" class="form-control" id="publisher" name="publisher"
					value="<%=inputData.getOrDefault("publisher", book.getPublisher())%>">
				<%
				if (errors != null && errors.containsKey("publisher")) {
				%>
				<div class="error"><%=errors.get("publisher")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="publishDate" class="form-label">Publish Date:</label> <input
					type="date" class="form-control" id="publishDate"
					name="publishDate"
					value="<%=inputData.getOrDefault("publishDate", book.getPublishDate())%>">
				<%
				if (errors != null && errors.containsKey("publishDate")) {
				%>
				<div class="error"><%=errors.get("publishDate")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="isbn" class="form-label">ISBN:</label> <input
					type="text" class="form-control" id="isbn" name="isbn"
					value="<%=inputData.getOrDefault("isbn", book.getIsbn())%>">
				<%
				if (errors != null && errors.containsKey("isbn")) {
				%>
				<div class="error"><%=errors.get("isbn")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="rating" class="form-label">Rating:</label> <input
					min="0" type="number" class="form-control" id="rating"
					name="rating"
					value="<%=inputData.getOrDefault("rating", String.valueOf(book.getRating()))%>">
				<%
				if (errors != null && errors.containsKey("rating")) {
				%>
				<div class="error"><%=errors.get("rating")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="description" class="form-label">Description:</label>
				<textarea class="form-control" id="description" name="description"><%=inputData.getOrDefault("description", book.getDescription())%></textarea>
				<%
				if (errors != null && errors.containsKey("description")) {
				%>
				<div class="error"><%=errors.get("description")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="imageLocation" class="form-label">Image
					Location:</label> <input type="file" class="form-control"
					id="imageLocation" name="imageLocation"
					value="<%=inputData.getOrDefault("imageLocation", book.getImageLocation())%>">
				<%
				if (errors != null && errors.containsKey("imageLocation")) {
				%>
				<div class="error"><%=errors.get("imageLocation")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="sold" class="form-label">Sold:</label> <input min="0"
					type="number" class="form-control" id="sold" name="sold"
					value="<%=inputData.getOrDefault("sold", String.valueOf(book.getSold()))%>">
				<%
				if (errors != null && errors.containsKey("sold")) {
				%>
				<div class="error"><%=errors.get("sold")%></div>
				<%
				}
				%>
			</div>

			<input type="submit" class="btn btn-primary" value="Update Book">
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
