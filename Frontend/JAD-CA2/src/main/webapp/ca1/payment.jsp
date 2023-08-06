<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
	if (session != null && session.getAttribute("loggedIn") != null) {
		// User is logged in

		
    
    String amountStr = (String) session.getAttribute("totalAmount");
    double amount = Double.parseDouble(amountStr);
    double gstRate = 0.08;
    double gstAmount = amount * gstRate;
    double totalAmount = amount + gstAmount;
    String publicKey = (String) session.getAttribute("stripePublicKey");
    String paymentType = (String) request.getAttribute("paymentType");
    System.out.println(publicKey);
    %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <!—customised CSS -->

<link href="css/payment.css" rel="stylesheet" />
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
  <div class="col-md-4 container bg-default">
			<h4 class="my-4">Select Payment Method</h4>
	<form action="../CheckoutServlet" method="post">
	<input type="hidden" name=totalAmount value="<%=totalAmount %>">
<input type="radio" name="paymentType" value="Stripe">
                   Stripe<br >
                 <hr class="my-4">  
                    <section class="container">
    <div class="promotion">
      <label for="promo-code">Have A Promo Code?</label>
      <input type="text" id="promo-code" /> <button type="button"></button>
    </div>

    <div class="summary">
      <ul>
        <li>Subtotal <span><%=amount %></span></li>
        <li>Tax <span><%=String.format("%.2f", gstAmount)%></span></li>
        <li class="total">Total <span><%=String.format("%.2f", totalAmount) %></span></li>
      </ul>
    </div>
                    
    <input type='hidden' value=<%=Double.toString(totalAmount) %> name='amount' />
    <script
       src='https://checkout.stripe.com/checkout.js' 
       class='stripe-button'
       data-key=<%=publicKey %> 
       data-amount=<%=totalAmount*100 %> 
       data-name='Checkout'
       data-description='Stripe Payment'
       data-image
         ='https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_sutg9AkJmbdBqw8qt2DTW3QAY7RzEQHPm3HyQXEenAsjRKbWdtzyp4yGmzRVFBd4Sro&usqp=CAU'
       data-locale='auto'
       data-zip-code='false'>
   </script>
   </section>
</form>
</div>
<%
	} else {
	// User is not an admin
	response.sendRedirect("login.jsp"); // Redirect to the home page
	}
	%>
</body>
</html>