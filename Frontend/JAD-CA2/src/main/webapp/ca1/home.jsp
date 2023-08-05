<%@ page import="java.util.List"%>
<%@page import="book.* , genre.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>



<%
// Hardcoded book ID for testing
String bookId = "1";

// Create a BookDao and get the book
BookDao bookDao = new BookDao();
GenreDao genreDao = new GenreDao();
//List<Book> allBooks = (List<Book>) request.getAttribute("bookArray");
List<Book> allBooks = bookDao.readAllBooks();

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <!—customised CSS -->

<link href="css/home.css" rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- <!—compiled & minified CSS -->

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css?family=Montserrat&display=swap"
	rel="stylesheet">
<!-- <!—jQuery library -->

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- <!—compile JavaScript -->

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>


</head>

<body>

 <jsp:include page="header.jsp" />


	<div id="carouselExampleControls" class="carousel slide m-5"
		data-bs-ride="carousel">
		<div class="carousel-inner">
			<div class="carousel-item active">
				<img
					src="https://asset.popular.com.sg/general/popular-online/2023/home/titbitscarnival_june-b.jpg"
					class="d-block w-75 mx-auto height" alt="...">
			</div>
			<div class="carousel-item">
				<img
					src="https://asset.popular.com.sg/general/popular-online/2023/home/logitech-b.jpg"
					class="d-block w-75 mx-auto height" alt="...">
			</div>
			<div class="carousel-item">
				<img
					src="https://asset.popular.com.sg/general/popular-online/2023/home/myreward-b.jpg"
					class="d-block w-75 mx-auto height" alt="...">
			</div>
		</div>
		<button class="carousel-control-prev" type="button"
			data-bs-target="#carouselExampleControls" data-bs-slide="prev">
			<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span
				class="visually-hidden">Previous</span>
		</button>
		<button class="carousel-control-next" type="button"
			data-bs-target="#carouselExampleControls" data-bs-slide="next">
			<span class="carousel-control-next-icon" aria-hidden="true"></span> <span
				class="visually-hidden">Next</span>
		</button>
	</div>



	<div class="container-fluid">
		<div class="browse" style="display: flex; justify-content: center">
			<h3>Browse</h3>
			<a href="filter.jsp"><img src="images/menu.png"
				class="filter-btn"></a>
		</div>
		<hr class="browse-line">
		<section id="movies"
			style="min-height: 100vh; width: 90%; margin-left: 110px; display: flex; flex-wrap: wrap; justify-content: space-between;">
			<%
			for (Book book : allBooks) {
			%>
			<div class="book-container">
				<a href="viewBook.jsp?id=<%=book.getBookId()%>"
					style="text-decoration: none;">
					<figure class="movie-figure"
						style="background-color: white; width: 14em; margin: 0.5em; box-shadow: 0 5px 15px gray; overflow: hidden; position: relative; cursor: pointer; transition: all 0.45s cubic-bezier(0.25, 0.46, 0.45, 0.94);">
						<img src="<%=request.getContextPath()%>/<%=book.getImageLocation()%>" alt="logo-image"
							style="height: 21em; width: 100%; border-style: none">
						<figcaption
							style="display: block; line-height: 2; padding: 0 0.8em; height: 140px;">
							<h5
								style="text-transform: uppercase; font-weight: 600; color: #737373; display: block; font-size: 1em; margin-block-start: 1em; margin-block-end: 1em; margin-inline-start: 0px; margin-inline-end: 0px; font-weight: bold;"><%=book.getTitle()%></h5>
							<p
								style="text-transform: capitalize; font-size: 1em; font-weight: 600; color: #dcbe00; display: block; margin-block-start: 1em; margin-block-end: 1em; margin-inline-start: 0px; margin-inline-end: 0px;"><%=genreDao.getGenre(book.getGenreId())%></p>
							<div class="rating-stars"
								style="display: flex; align-items: center;">
								<div class="rating-stars">
									<%-- Generate the star rating based on book's rating --%>
									<%
										for (int i = 1; i < book.getRating(); i++) {
									%>
									<span class="fa fa-star checked"></span>
									<%
									}
									%>
									<%
									for (int i = (int) book.getRating(); i <= 4; i++) {
									%>
									<span class="fa fa-star"></span>
									<%
									}
									%>
								</div>
								<h4 style="color: black; display: block;"><%=book.getRating()%></h4>
							</div>
						</figcaption>
					</figure>
				</a>
			</div>
			<%
			}
			%>
		</section>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>


</body>
</html>