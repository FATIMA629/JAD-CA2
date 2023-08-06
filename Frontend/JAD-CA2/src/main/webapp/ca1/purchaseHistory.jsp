<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List"%>
<%@page import="order.*"%>
    <%
	if (session != null && session.getAttribute("loggedIn") != null) {
		// User is logged in

		// Check if the user is an admin
		String role = (String) session.getAttribute("role");
		if (!role.equals("admin")) {
			// User is a registered user
    
    int userId = (int) session.getAttribute("userId");
    String username = (String) session.getAttribute("username");
	String phone = (String) session.getAttribute("phone");
	
	OrderDao orderDao = new OrderDao();
	List<Order> orderList = orderDao.getAllOrdersByUserId(userId);
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <!—customised CSS -->

<link href="css/purchaseHistory.css" rel="stylesheet" />
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
<div class="container">
<div class="row">
<div class="col-md-12">
<div class="row">
<div class="tabs">
<div class="tab-content">
<div id="salesHistory">
<h4>Sales History</h4>

<%
if(!orderList.isEmpty()) {

int orderNumber = orderList.size();

for(Order order: orderList) {
	System.out.println(orderNumber);
%>
<div id="salesHistoryTab">
<h5>Order # <%=orderNumber %></h5>
<h6>Date Purchased: <%=order.getOrderDate() %></h6>
<h6>Order Status: <%=order.getOrderStatus() %></h6>
<h6 style="text-decoration:underline; font-weight:bold">Delivery Details:</h6>
<h6>Address: <%=order.getShippingAddress().getAddress1() %></h6>
<h6>Postal Code: <%=order.getShippingAddress().getPostalCode() %></h6>
<h6>Username: <%=username %></h6>
<h6>Contact Number: <%=phone %></h6>
<div class="row">
                                    <div class="col-md-12">
                                        <div class="row featured-boxes">
                                            <div class="col-md-12">
                                                <div class="featured-box featured-box-secundary featured-box-cart">
                                                    <div class="box-content">
                                                        <table cellspacing="0" class="shop_table cart">
                                                            <thead>
                                                                <tr>
                                                                    <th class="product-thumbnail" style="width: 25%">Image</th>
                                                                    <th class="product-name" style="width: 38%">Product</th>
                                                                    <th class="product-price" style="width: 15%">Price</th>
                                                                    <th class="product-quantity" style="width: 15%">Quantity</th>
                                                                    <th class="product-subtotal" style="width: 15%">Subtotal</th>
                                                                    <th class="product-review" style="width: 15%">Action</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                            <%
                                                            List<OrderItem> orderItemsList = order.getOrderItems();
                                                            for (OrderItem orderItem: orderItemsList) {	
                                                            %>
                                                            <tr class="cart_table_item">
                                            <td class="product-thumbnail">
                                                <a href="viewBook.jsp?id=<%=orderItem.getBook().getBookId() %>"><img width="100" height="100" alt="" class="img-responsive" src=<%=orderItem.getBook().getImageLocation() %>></a>
                                            </td>
                                            <td class="product-name">
                                                <a class="productDetails" href="viewBook.jsp?id=<%=orderItem.getBook().getBookId() %>"><%=orderItem.getBook().getTitle() %></a>
                                            </td>
                                            <td class="product-price">
                                                $<span class="amount"><%=orderItem.getBook().getPrice() %></span>
                                            </td>
                                            <td class="product-quantity">
                                                <span class="qty"><%=orderItem.getQuantity() %></span>
                                            </td>
                                            <td class="product-subtotal">
                                                $<span class="amount"><%=orderItem.getBook().getPrice() * orderItem.getQuantity() %></span>
                                            </td>
                                            <td>
                                             <div style="margin: 0 0 0 10px; display: flex; align-items: center; text-overflow: ellipsis;">
                                             <form method="get" action="../AddRatingServlet">
                                             <input type="hidden" name="bookId" value="<%=orderItem.getBook().getBookId()%>">
    <button class="btn btn--primary position" type="submit">
    Rate
    </button>
    </form>
    </div>
								
                                            </td>
                                        </tr>
                                        <%
                                                            }
                                        %>
                                         <tr>
                                                                    <td></td>
                                                                    <td></td>
                                                                    <td></td>
                                                                    <td class="product-subtotal" style="font-weight: bold; color: #cc3b33">Total:</td>
                                                                    <td class="product-subtotal" style="font-weight: bold">
                                                                        $<span class="amount" id="finalPrice" name="finalPrice"><%=order.getTotalPrice() %></span>
                                                                    </td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
<%
orderNumber--;
}
%>
</div>
</div>
<%
} else {
%>
<h4 style="text-align: center">Sales History is empty</h4>
<%
}
%>
</div>
</div>
</div>
</div>
</div>
</div>
<%
	} else {
	// User is not an admin
	response.sendRedirect("login.jsp"); // Redirect to the home page
	}
	} else {
	// User is not logged in
	response.sendRedirect("login.jsp"); // Redirect to the home page
	}
	%>
</body>
</html>