<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    String id = (String) request.getAttribute("amount");
    String status = (String) request.getAttribute("email");
    String chargeId = (String) request.getAttribute("token");
    String balance = (String) request.getAttribute("currency");
    %>
<!DOCTYPE html>
<html xmlns='http://www.w3.org/1999/xhtml' xmlns:th='http://www.thymeleaf.org'>
    <head>
        <title>Result</title>
    </head>
    <body>
        
       
            <h3 style='color: green;'>Success!</h3>
            <div>Id.: <span><%=id %></span></div>
            <div>Status: <span><%=status %></span></div>
            <div>Charge id.: <span><%=chargeId %></span></div>
            <div>Balance transaction id.: <span><%=balance %></span></div>
        <a href='/checkout.html'>Checkout again</a>
    </body>
</html>