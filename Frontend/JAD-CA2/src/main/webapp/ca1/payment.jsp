<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    int amount = (int) request.getAttribute("totalPrice");
    String publicKey = (String) request.getAttribute("stripePublicKey");
    String currency = (String) request.getAttribute("currency");
    System.out.println(currency);
    System.out.println(publicKey);
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="CheckoutServlet" method="post">
    <input type='hidden' value=<%=amount %> name='amount' />
    <label>Price:<span><%= amount/100 %></span></label>
    <script
       src='https://checkout.stripe.com/checkout.js' 
       class='stripe-button'
       data-key=<%=publicKey %> 
       data-amount=<%=amount %> 
       data-currency=<%=currency %>
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