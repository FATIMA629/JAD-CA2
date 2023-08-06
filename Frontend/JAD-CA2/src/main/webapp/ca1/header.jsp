<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="user.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
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
</head>
<body>


	<%
	if (session.getAttribute("role") != null && session.getAttribute("role").equals("admin")) {
	%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="home.jsp"> <img
				src="../images/home.png" class="img-fluid" id="navImage">
			</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
				aria-controls="navbarNavAltMarkup" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
				<div class="navbar-nav mx-auto">
					<!-- Added 'mx-auto' class for center alignment -->
					<form class="search-container" action="../SearchBookServlet">
						<input type="text" name="search" id="search-bar"
							placeholder="Search anything...">
						<button type="submit" class="search-btn">
							<img class="search-icon"
								src="http://www.endlessicons.com/wp-content/uploads/2012/12/search-icon.png">
						</button>
					</form>
					<a href="adminDashboard.jsp"> <!-- Add a link to the admin dashboard page -->
						<button class="btn btn-primary">Admin Dashboard</button> <!-- Add a button for admin dashboard -->
					</a>
					<a href="AdminReporting&Inquiry.jsp"> <!-- Add a link to the admin dashboard page -->
						<button class="btn btn-primary">Admin Reporting & Inquiry</button> <!-- Add a button for admin dashboard -->
					</a>
					 <a href="cart.jsp"> <img src="../images/cart.png"
						class="img-fluid" id="cartImage">
					</a>
					<div class="cart-drawer">
						<div class="profile-icon">
							<img src="../images/profile.png" class="img-fluid" id="profileImage">
							<div class="profile-prompt guest">
								<div class="profile">
									<span class="dot"><img class="profile-picture"
										src="../images/image-removebg-preview (39).png" /></span> <a
										href="userEditUser.jsp"><span class="profile-status">Admin</span></a>
								</div>
								<hr>
								<span><a href="purchaseHistory.jsp">My Purchases</a></span>
								<hr>
								<button
									style="background: transparent; cursor: pointer; border: none;">
									<a href="logout.jsp">Log Out</a>
								</button>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</nav>
	<%
	} else if (session.getAttribute("role") != null && session.getAttribute("role").equals("member")) {
	%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<!-- Navbar content for logged-in user -->

		<div class="container-fluid">
			<a class="navbar-brand" href="home.jsp"> <img
				src="../images/home.png" class="img-fluid" id="navImage">
			</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
				aria-controls="navbarNavAltMarkup" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
				<div class="navbar-nav mx-auto">
					<!-- Added 'mx-auto' class for center alignment -->
					<form class="search-container" action="../SearchBookServlet">
						<input type="text" name="search" id="search-bar"
							placeholder="Search anything...">
						<button type="submit" class="search-btn">
							<img class="search-icon"
								src="http://www.endlessicons.com/wp-content/uploads/2012/12/search-icon.png">
						</button>
					</form>
					<a href="cart.jsp"> <img src="../images/cart.png"
						class="img-fluid" id="cartImage">
					</a>
					
					<div class="cart-drawer">
						<div class="profile-icon">
							<img src="../images/profile.png" class="img-fluid" id="profileImage">
							<div class="profile-prompt guest">
								<div class="profile">
									<span class="dot"><img class="profile-picture"
										src="../images/image-removebg-preview (39).png" /></span> <a
										href="userEditUser.jsp"><span class="profile-status">User</span></a>
								</div>
								<hr>
								<span><a href="purchaseHistory.jsp">My Purchases</a></span>
								<hr>
								<button
									style="background: transparent; cursor: pointer; border: none;">
									<a href="logout.jsp">Log Out</a>
								</button>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</nav>
	<%
	} else {
	%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<!-- Navbar content for guest -->
		<div class="container-fluid">
			<a class="navbar-brand" href="home.jsp"> <img
				src="../images/home.png" class="img-fluid" id="navImage">
			</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
				aria-controls="navbarNavAltMarkup" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
				<div class="navbar-nav mx-auto">
					<!-- Added 'mx-auto' class for center alignment -->
					<form class="search-container" action="../SearchBookServlet" method="get">
						<input type="text" name="search" id="search-bar"
							placeholder="Search anything...">
						<button type="submit" class="search-btn">
							<img class="search-icon"
								src="http://www.endlessicons.com/wp-content/uploads/2012/12/search-icon.png">
						</button>
					</form>

					<a href="cart.jsp"> <img src="../images/cart.png"
						class="img-fluid" id="cartImage">
					</a>
					<div class="cart-drawer">
						<div class="profile-icon">
							<img src="../images/profile.png" class="img-fluid" id="profileImage">
							<div class="profile-prompt guest">
								<div class="profile">
									<span class="dot"><img class="profile-picture"
										src="../images/image-removebg-preview (39).png" /></span> <a href="#"><span
										class="profile-status">Guest</span></a>
								</div>
								<hr>
								<span><a href="login.jsp">Log In</a></span>
								<hr>
								<span><a href="userRegistration.jsp">Register</a></span>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>

	</nav>
	<%
	}
	%>
	
</body>
</html>