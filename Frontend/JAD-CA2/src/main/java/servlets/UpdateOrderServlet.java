package servlets;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import order.Order;
import order.OrderDao;
import order.OrderItem;
import user.*;
import book.*;

@WebServlet("/UpdateOrderServlet")
public class UpdateOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderDao orderDao;

	public UpdateOrderServlet() {
		super();
		orderDao = new OrderDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDao userDao = new UserDao();
		BookDao bookDao = new BookDao();
		
		request.getSession().removeAttribute("errors");
		request.getSession().removeAttribute("inputData");
		request.getSession().removeAttribute("success");

		System.out.println("UpdateOrderServlet: doPost method entered");

		String orderId = request.getParameter("orderId");
		System.out.println("orderId: " + orderId);
		String userId = request.getParameter("userId");
		System.out.println("userId: " + userId);
		String totalPrice = request.getParameter("totalPrice");
		System.out.println("totalPrice: " + totalPrice);
		String orderDateStr = request.getParameter("orderDate");
		System.out.println("orderDateStr: " + orderDateStr);
		String orderStatus = request.getParameter("orderStatus");
		System.out.println("orderStatus: " + orderStatus);

		// Print all address-related parameters
		String country = request.getParameter("country");
		System.out.println("country: " + country);
		String address1 = request.getParameter("address1");
		System.out.println("address1: " + address1);
		String address2 = request.getParameter("address2");
		System.out.println("address2: " + address2);
		String district = request.getParameter("district");
		System.out.println("district: " + district);
		String city = request.getParameter("city");
		System.out.println("city: " + city);
		String postalCode = request.getParameter("postalCode");
		System.out.println("postalCode: " + postalCode);

		Map<String, String> errors = new HashMap<>();

		// Validate fields
		if (userId == null || userId.isEmpty()) {
			errors.put("userId", "User ID is required");
		} else if (!userDao.isUserExist(Integer.parseInt(userId))) { // Validate userId using userDao
			errors.put("userId", "Invalid User ID");
		}
		if (totalPrice == null || !isValidDecimal(totalPrice)) {
			errors.put("totalPrice", "Total Price should be a valid decimal number");
		}
		if (orderDateStr == null || orderDateStr.isEmpty()) {
			errors.put("orderDate", "Order Date is required");
		} else {
			java.util.Date orderDate = convertToDate(orderDateStr);
			if (orderDate == null) {
				errors.put("orderDate", "Invalid date format. Please use yyyy-MM-dd.");
			} else if (orderDate.after(new Date())) {
				errors.put("orderDate", "Order date cannot be in the present or future");
			}
		}
		if (orderStatus == null || !orderStatus.matches("Shipped|Processing|Cancelled|Delivered")) {
			errors.put("orderStatus", "Invalid order status");
		}
		if (isNullOrEmpty(request.getParameter("country")) || isNullOrEmpty(request.getParameter("address1"))
				|| isNullOrEmpty(request.getParameter("address2")) || isNullOrEmpty(request.getParameter("district"))
				|| isNullOrEmpty(request.getParameter("city"))) {
			errors.put("address", "Country, Address1, Address2, District, City must not be null");
		}
		if (request.getParameter("postalCode") == null || !request.getParameter("postalCode").matches("\\d{6}")) {
			errors.put("postalCode", "Postal code must be 6 digits");
		}

		// If there are no errors, update the order in the database
		Order order = orderDao.getOrderById(Integer.parseInt(orderId));
		List<OrderItem> orderItems = order.getOrderItems();
		if (order != null) {
			order.setUserId(Integer.parseInt(userId));
			order.setTotalPrice(Double.parseDouble(totalPrice));
			order.setOrderDate(convertToDate(orderDateStr));
			order.setOrderStatus(orderStatus);

			// Update the shipping address fields
			order.getShippingAddress().setCountry(request.getParameter("country"));
			order.getShippingAddress().setAddress1(request.getParameter("address1"));
			order.getShippingAddress().setAddress2(request.getParameter("address2"));
			order.getShippingAddress().setDistrict(request.getParameter("district"));
			order.getShippingAddress().setCity(request.getParameter("city"));
			order.getShippingAddress().setPostalCode(request.getParameter("postalCode"));

			// Update the order items fields
			for (OrderItem item : orderItems) {
				int itemId = item.getOrderItemId(); // Get the order item ID if you have one
				String bookId = request.getParameter("bookId_" + itemId);
				String quantity = request.getParameter("quantity_" + itemId);
				String unitPrice = request.getParameter("unitPrice_" + itemId);

				// Validate bookId using bookDao
				if (!bookDao.isBookExist(Integer.parseInt(bookId))) {
					errors.put("bookId_" + itemId, "Invalid Book ID");
				}
				// Validate quantity & unitPrice
				if (!isValidDecimal(quantity) || !isValidDecimal(unitPrice)) {
					errors.put("quantityOrPrice_" + itemId, "Quantity and Unit Price must be valid numbers");
				}

				// Update the order item properties
				item.getBook().setBookId(Integer.parseInt(bookId));
				item.setQuantity(Integer.parseInt(quantity));
				item.setUnitPrice(Double.parseDouble(unitPrice));
			}

			// If there are errors, store them in the session and redirect back to the form
			if (!errors.isEmpty()) {
				request.getSession().setAttribute("errors", errors);
				response.sendRedirect("ca1/updateOrder.jsp"); // Update the path to your edit order form
				return;
			}

			// Call the DAO method to update the order in the database
			orderDao.updateOrder(order);

			// Set a success message in the session and redirect to the appropriate page
			request.getSession().setAttribute("success", "Order updated successfully");
			response.sendRedirect("ca1/adminDashboard.jsp"); // Update the path to your success page
		} else {
			// Handle the case where the order is not found
			// You can set an error message and redirect to an error page if needed
		}
	}

	private boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}

	// Helper method to check if a string represents a valid decimal number
	private boolean isValidDecimal(String str) {
		Pattern decimalPattern = Pattern.compile("\\d+(\\.\\d+)?");
		return decimalPattern.matcher(str).matches();
	}

	// Helper method to convert a string in the format yyyy-MM-dd to java.util.Date
	private java.util.Date convertToDate(String dateStr) {
		try {
			return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (java.text.ParseException e) {
			return null;
		}
	}
}
