<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    String amountStr = (String) session.getAttribute("totalPrice");
    double amount = Double.parseDouble(amountStr);
    String publicKey = (String) request.getAttribute("stripePublicKey");
    System.out.println(publicKey);
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<input type="radio" name="paymentType" value="Cash">
                   Cash<br>
<input type="radio" name="paymentType" value="Cash">
                   Stripe<br>
	<form action="CheckoutServlet" method="post">
    <input type='hidden' value=<%=Double.toString(amount) %> name='amount' />
    <label>Price:<span><%= amount %></span></label>
    <script
       src='https://checkout.stripe.com/checkout.js' 
       class='stripe-button'
       data-key=<%=publicKey %> 
       data-amount=<%=amount %> 
       data-name='Baeldung'
       data-description='Spring course checkout'
       data-image
         ='https://www.baeldung.com/wp-content/themes/baeldung/favicon/android-chrome-192x192.png'
       data-locale='auto'
       data-zip-code='false'>
   </script>
</form>
</body>
</html>