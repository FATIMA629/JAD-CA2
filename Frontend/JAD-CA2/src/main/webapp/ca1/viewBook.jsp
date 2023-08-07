<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="book.* "%>
<%@page import="rating.* "%>
<%@page import="user.* "%>
<%@ page import="java.util.*"%>

<%
System.out.println("bookId in jsp is " + session.getAttribute("bookId"));
// Hardcoded book ID for testing
int bookId = (int) session.getAttribute("bookId");

// Create a BookDao and get the book
BookDao bookDao = new BookDao();
Book book = bookDao.getBookById(bookId);
RatingDao ratingDao = new RatingDao();
String selected = request.getParameter("selected");
System.out.println(selected);
double AvgRating = ratingDao.getAverageRatingForBook(bookId);
List<Rating> ratingList = (List<Rating>) session.getAttribute("ratingList");
Map<String, String> errors = (Map<String, String>) session.getAttribute("errors");
UserDao userDao = new UserDao();
%>

<!DOCTYPE html>
<html>

<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <!—customised CSS -->

<link href="css/books.css" rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

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
<!-- Axios -->
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
</head>

<body>
	<jsp:include page="header.jsp" />

	<div class="container-fluid p-5" id="product-details"
		style="background-color: #f1f1f1; height: 100vh;">
		<div class="container p-2"
			style="background-color: white; height: 100%;">
			<div class="row" style="height: 100%;">
				<div class="col-4 p-3" style="height: 100%;">
					<img
						src="<%=request.getContextPath()%>/<%=book.getImageLocation()%>"
						alt="book" style="height: 100%; width: 100%;" />
				</div>
				<div class="col-8 p-4">
					<h2 class="book-name"><%=book.getTitle()%></h2>
					<div class="ratings-section">
						<a href="#rating-section"
							style="text-decoration: none; font-size: 18px; font-weight: bold; margin-right: 8px; color: black;">
							<p class="rating-value">
								<u><%=String.format("%.1f", AvgRating)%></u>
							</p>
						</a> <a href="#rating-section"
							style="text-decoration: none; color: black;">
							<div class="rating-stars">
								<%-- Generate the star rating based on the book's rating --%>
								<%
								for (int i = 1; i <= 5; i++) {
								%>
								<%
								if (i <= AvgRating) {
								%>
								<span class="fa fa-star checked"></span>
								<%
								} else {
								%>
								<span class="fa fa-star"></span>
								<%
								}
								%>
								<%
								}
								%>
							</div>
						</a> <span class="rating-line"></span>
						<p class="rating-amount">
							<%=book.getQuantity()%>
							<span class="rating-word"> Quantity</span>
						</p>
						<span class="rating-line"></span>
						<p class="amount-sold"><%=book.getSold()%>
							<span class="sold-word">Sold</span>
						</p>
					</div>
					<div class="book-description-wrapper">
						<hr>
						<p class="book-description-header">Book Description:</p>
						<div class="book-description" id="book-description">
							<p><%=book.getDescription()%>

							</p>
						</div>


						<div class="row">
							<div class="col-3 seperator">
								<p class="book-isbn">ISBN:</p>
								<p class="book-isbn-description"><%=book.getIsbn()%></p>
							</div>

							<div class="col-3 seperator">

								<p class="book-author">Author(s):</p>
								<p class="book-author-description"><%=book.getAuthor()%></p>


							</div>
							<div class="col-3 seperator">
								<p class="book-publisher">Publisher(s):</p>
								<p class="book-publisher-description"><%=book.getPublisher()%></p>
							</div>
							<div class="col-3">
								<p class="book-publisher">Publish Date:</p>
								<p class="book-publisher-description"><%=book.getPublishDate()%></p>
							</div>
						</div>

						<hr>
					</div>
					<p class="book-amount"><%=book.getPrice()%></p>
					<form action="../AddToCartServlet" method="post">
						<div class="quantity-wrapper">
							<span class="quantity-text">Quantity: </span>
							<div class="quantity">
								<button class="minus-button" type="button">-</button>
								<input type="text" name="cart-quantity" value="1"
									class="quantity-input">
								<button class="plus-button" type="button">+</button>
							</div>
						</div>
						<input type="hidden" name="id" value="<%=book.getBookId()%>">
						<button type="submit" class="buy--btn">
							<img src="../images/output-onlinepngtools.png" alt="cart-image"
								class="add-to-cart"> ADD TO CART
						</button>
							<%
				if (errors != null && errors.containsKey("quantity")) {
				%>
				<div class="error"><%=errors.get("quantity")%></div>
				<%
				}
				%>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid p-5" class="review-section"
		id="rating-section" style="background-color: #f1f1f1; height: 100vh;">
		<div class="container p-2"
			style="background-color: white; height: 100%;">
			<div class="row"
				style="display: flex; justify-content: center; align-items: center;">
				<p
					style="font-weight: bold; font-size: 18px; margin-top: 15px; margin-left: 20px">Product
					Ratings</p>
				<div class="col-11 mx-auto"
					style="background-color: #fffbf8; min-height: 5rem; border: 1px solid #f9ede5; margin-bottom: 1rem; display: flex; align-items: center; border-radius: 2px; box-sizing: border-box; padding: 1.875rem;">

					<div style="text-align: center; margin-right: 1.875rem;">
						<p style="color: #ee4d2d; font-size: 1.125rem;"><%=String.format("%.1f", AvgRating)%>
							out of 5
						</p>
						<div style="font-size: 1.25rem; margin-top: 0.625rem;">
							<%-- Generate the star rating based on the book's rating --%>
							<%
							for (int i = 1; i <= 5; i++) {
							%>
							<%
							if (i <= AvgRating) {
							%>
							<span class="fa fa-star checked"></span>
							<%
							} else {
							%>
							<span class="fa fa-star"></span>
							<%
							}
							%>
							<%
							}
							%>
						</div>
					</div>
					<div style="flex: 1; margin-left: 0.9375rem;">
						<form id="filterForm" action="../FilterRatingsServlet"
							method="post">
							<input type="hidden" name="selectedButton" id="selectedButton"
								value="All"> <input type="hidden" name="bookId"
								value="<%=bookId%>">
							<button id="button1">All</button>
							<button id="button2">highest</button>
							<button id="button3">lowest</button>
						</form>
					</div>
				</div>
				<%
				for (Rating rating : ratingList) {
				%>
				<div style="display: block">
					<div class="comment-section">
						<div class="comment-avatar"
							style="width: 2.5rem; margin-right: 0.625rem; text-align: center;">
							<div style="border: 0; width: 2.5rem; height: 2.5rem;">
								<div
									style="width: 100%; position: relative; padding-top: 100%; background-color: #f5f5f5; border-radius: 50%; overflow: hidden;">
									<img
										src="https://cdn-icons-png.flaticon.com/512/3106/3106773.png"
										alt="profile-icon"
										style="stroke: #c6c6c6; position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); font-size: 1.5rem; font-weight: 400; line-height: 2rem; -webkit-font-smoothing: antialiased;">
								</div>
							</div>
						</div>
						<div class="comment-main-section" style="flex: 1">
							<div class="comment-author"
								style="text-decoration: none; color: rgba(0, 0, 0, .87); font-size: 0.80rem;">
								<%=userDao.getUserNameById(rating.getUserId())%></div>
							<div class="comment-star" style="display: flex;">
								<div class="comment-star-rating">
									<%
									for (int i = 1; i <= 5; i++) {
									%>
									<%
									if (i <= rating.getRating()) {
									%>
									<span class="fa fa-star checked"></span>
									<%
									} else {
									%>
									<span class="fa fa-star"></span>
									<%
									}
									%>
									<%
									}
									%>
								</div>

							</div>
							<div class="comment-text"
								style="position: relative; box-sizing: border-box; margin: 0.5rem 0; font-size: 0.875rem; line-height: 1.25rem; color: rgba(0, 0, 0, .87); word-break: break-word; white-space: pre-wrap;">
								<%=rating.getComment()%></div>
						</div>
						<div class="comment-feedback"
							style="position: relative; top: 90px; right: 25px">
							<button class="comment-like-btn">
								<i class="far fa-thumbs-up"></i> <span
									style="font-size: 0.875rem; position: relative; bottom: 3px; color: grey">Helpful?</span>
							</button>
						</div>
					</div>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</div>



	<script>
		var quantityCounter = document.querySelector('.quantity-input');
		var minusButton = document.querySelector('.minus-button');
		var plusButton = document.querySelector('.plus-button');

		minusButton.addEventListener('click', function() {
			var currentQuantity = parseInt(quantityCounter.value);
			if (currentQuantity > 1) {
				quantityCounter.value = currentQuantity - 1;
			}
		});

		plusButton.addEventListener('click', function() {
			var currentQuantity = parseInt(quantityCounter.value);
			quantityCounter.value = currentQuantity + 1;
		});

		const likeButton = document.querySelector('.comment-like-btn');
		console.log(likeButton)
		likeButton.addEventListener('click', function() {
			if (likeButton.classList.contains('active')) {
				likeButton.classList.remove('active');
			} else {
				likeButton.classList.add('active');
			}
		});

		const button1 = document.getElementById('button1');
		const button2 = document.getElementById('button2');
		const button3 = document.getElementById('button3');

		// Add click event listeners
		button1.addEventListener('click', function() {
			button1.classList.add('active');
			button2.classList.remove('active');
			button3.classList.remove('active');
			document.getElementById('selectedButton').value = "All";
		});

		button2.addEventListener('click', function() {
			button1.classList.remove('active');
			button2.classList.add('active');
			button3.classList.remove('active');
			document.getElementById('selectedButton').value = "highest";
		});

		button3.addEventListener('click', function() {
			button1.classList.remove('active');
			button2.classList.remove('active');
			button3.classList.add('active');
			document.getElementById('selectedButton').value = "lowest";
		});
	</script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>
</body>

</html>