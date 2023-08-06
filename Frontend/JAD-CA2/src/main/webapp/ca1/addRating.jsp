<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="book.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.HashMap, java.util.Map"%>
    <%
    int bookId = (int) request.getAttribute("bookId");
    BookDao bookDao = new BookDao();
	Book book = bookDao.getBookById(bookId);
	Map<String, String> inputData = (Map<String, String>) session.getAttribute("inputData");
	Map<String, String> errors = (Map<String, String>) session.getAttribute("errors");
	String successMessage = (String) session.getAttribute("success");

	
	%>

	<%
	if (successMessage != null) {
	%>
	<script>
alert('<%=successMessage%>
		');
	</script>
	<%
	}
	%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Rating</title>
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
</head>
<body>
<div class="container mt-5">
		<h2 class="mb-4">Add Rating</h2>
		<form method="post" action="AddRatingServlet">
			<input type="hidden" name="bookId" value="<%=bookId%>">

			<div class="mb-3">
				<label for="rating" class="form-label">Rating:</label> <input
					type="text" class="form-control" id="rating" name="rating"
					value="<%=inputData != null ? inputData.get("rating") : ""%>">
				<%
				if (errors != null && errors.containsKey("rating")) {
				%>
				<div class="error"><%=errors.get("rating")%></div>
				<%
				}
				%>
			</div>

			<div class="mb-3">
				<label for="comment" class="form-label">Comment:</label> <input
					type="text" class="form-control" id="comment" name="comment"
					value="<%=inputData != null ? inputData.get("comment") : ""%>">
				<%
				if (errors != null && errors.containsKey("comment")) {
				%>
				<div class="error"><%=errors.get("comment")%></div>
				<%
				}
				%>
			</div>
			
			<input type="submit" class="btn btn-primary" value="Add Rating">
			</form>
			</div>
</body>
</html>