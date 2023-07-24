<%@ page import="java.util.List"%>
<%@page import="Books.Book , Books.BookDao , Geners.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
// Hardcoded book ID for testing
String bookId = "1";

// Create a BookDao and get the book
BookDao bookDao = new BookDao();
GenreDao genreDao = new GenreDao();
List<Genre> genreList = genreDao.getAllGenres();

String searchInput = request.getParameter("searchInput");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <!—customised CSS -->

<link href="css/filter.css" rel="stylesheet" />
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

	<div class="container-fluid filter">
			<h4 class="header">Filter</h4>
		<form action="../GetFilteredBook">
		<div class="checkbox-container">
			<%
			for (Genre genre : genreList) {
			%>
			<label>
                <input type="checkbox" name="genre" value="<%=Integer.toString(genre.getGenreId()) %>">
                <%=genre.getGenreName() %>
            </label>
            <br>
        <% } %>
        </div>
      
     <div class="length range__slider" data-min="1" data-max="40">
		<div class="length__title field-title" data-length='0'>Max Price:</div>
		<input id="slider" type="range" min="1" max="40" value="20" name="price" />
	</div>
	<div class="btn-container">
<button type="reset" class="btn btn-danger">Reset</button>
<button type="submit" class="btn btn-success">Apply</button>
</div>
</form>
	
	</div>
	
	
	
	
	
	
	
	
	
	
	<script>
	console.clear();
	
	const sliderProps = {
		fill: "#0B1EDF",
		background: "rgba(255, 255, 255, 0.214)",
	};

	// Selecting the Range Slider container which will effect the LENGTH property of the password.
	const slider = document.querySelector(".range__slider");

	// Text which will show the value of the range slider.
	const sliderValue = document.querySelector(".length__title");

	// Using Event Listener to apply the fill and also change the value of the text.
	slider.querySelector("input").addEventListener("input", event => {
		sliderValue.setAttribute("data-length", event.target.value);
		applyFill(event.target);
	});
	// Selecting the range input and passing it in the applyFill func.
	applyFill(slider.querySelector("input"));
	// This function is responsible to create the trailing color and setting the fill.
	function applyFill(slider) {
		const percentage = (100 * (slider.value - slider.min)) / (slider.max - slider.min);
		const bg = `linear-gradient(90deg, ${sliderProps.fill} ${percentage}%, ${sliderProps.background} ${percentage +
				0.1}%)`;
		slider.style.background = bg;
		sliderValue.setAttribute("data-length", slider.value);
	}

	// Selecting all the DOM Elements that are necessary -->
	// The Viewbox where the result will be shown
	const resultEl = document.getElementById("result");
	// The input slider, will use to change the length of the password
	const lengthEl = document.getElementById("slider");


	
	</script>
	
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>


</body>
</html>