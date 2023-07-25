package com.bookstore.storews.controller;

import org.springframework.web.bind.annotation.*;
import com.bookstore.storews.order.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

	@RequestMapping(method = RequestMethod.GET, path = "/getOrderById/{orderId}")
	public Order getOrderById(@PathVariable("orderId") int orderId) {
		try {
			OrderDao orderDao = new OrderDao();
			return orderDao.getOrderById(orderId);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
	}

	@RequestMapping(path = "/createOrder", consumes = "application/json", method = RequestMethod.POST)
	public boolean createOrder(@RequestBody Order order) {
		try {
			// Print individual fields for debugging
			System.out.println("Received Order ID: " + order.getOrderId());
			System.out.println("Received User ID: " + order.getUserId());
			System.out.println("Received Total Price: " + order.getTotalPrice());
			System.out.println("Received Order Date: " + order.getOrderDate());
			System.out.println("Received Order Status: " + order.getOrderStatus());
			System.out.println("Received Shipping Address: " + order.getShippingAddress());
			System.out.println("Received Billing Address: " + order.getBillingAddress());
			System.out.println("Received Postal Code: " + order.getPostalCode());
			System.out.println("Received Country: " + order.getCountry());

			OrderDao orderDao = new OrderDao();

			// First, create the order in the orders table
			int generatedOrderId = orderDao.createOrder(order);

			// If the order was successfully created, create the associated order items
			if (generatedOrderId != -1) { // Check if the order ID is valid
				OrderItemDao orderItemDao = new OrderItemDao();
				for (OrderItem orderItem : order.getOrderItems()) {
					// Set the generated order ID for each order item
					orderItem.setOrderId(generatedOrderId);
					orderItemDao.createOrderItem(orderItem);
				}
			}

			return (generatedOrderId != -1); // Return true if the order ID is valid
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return false;
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getAllOrders")
	public List<Order> getAllOrders() {
		try {
			OrderDao orderDao = new OrderDao();
			return orderDao.getAllOrders();
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return new ArrayList<>();
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/updateOrder")
	public boolean updateOrder(@RequestBody Order order) {
		try {
			OrderDao orderDao = new OrderDao();

			// Print the received order for debugging
			System.out.println("Received Order ID: " + order.getOrderId());
			System.out.println("Received User ID: " + order.getUserId());
			System.out.println("Received Total Price: " + order.getTotalPrice());
			System.out.println("Received Order Date: " + order.getOrderDate());
			System.out.println("Received Order Status: " + order.getOrderStatus());
			System.out.println("Received Shipping Address: " + order.getShippingAddress());
			System.out.println("Received Billing Address: " + order.getBillingAddress());
			System.out.println("Received Postal Code: " + order.getPostalCode());
			System.out.println("Received Country: " + order.getCountry());
			
	        // Print the order items information
	        for (OrderItem orderItem : order.getOrderItems()) {
	            System.out.println("Received OrderItem ID: " + orderItem.getOrderItemId());
	            System.out.println("Received Book ID: " + orderItem.getBookId());
	            System.out.println("Received Quantity: " + orderItem.getQuantity());
	            System.out.println("Received Unit Price: " + orderItem.getUnitPrice());
	        }	

			// First, update the order in the orders table
			boolean orderUpdated = orderDao.updateOrder(order);

			// Logging statements
			if (orderUpdated) {
				System.out.println("Order with ID " + order.getOrderId() + " updated successfully.");
			} else {
				System.out.println("Failed to update order with ID " + order.getOrderId());
			}

			// If the order was successfully updated, update the associated order items
			if (orderUpdated) {
				OrderItemDao orderItemDao = new OrderItemDao();
				for (OrderItem orderItem : order.getOrderItems()) {
					if (orderItem.getOrderItemId() > 0) {
						orderItemDao.updateOrderItem(orderItem);
						System.out.println("Order item with ID " + orderItem.getOrderItemId() + " updated successfully.");
					} else {
						System.out.println("Failed to update order item with invalid data: " + orderItem);
					}
				}
			}

			return orderUpdated;
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return false;
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/deleteOrder/{orderId}")
	public boolean deleteOrder(@PathVariable("orderId") int orderId) {
		try {
			OrderDao orderDao = new OrderDao();
			return orderDao.deleteOrder(orderId);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return false;
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getOrderItemsByOrderId/{orderId}")
	public List<OrderItem> getOrderItemsByOrderId(@PathVariable("orderId") int orderId) {
		try {
			OrderDao orderDao = new OrderDao();
			return orderDao.getOrderItemsByOrderId(orderId);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return new ArrayList<>();
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/updateOrderStatus/{orderId}/{orderStatus}")
	public boolean updateOrderStatus(@PathVariable("orderId") int orderId,
			@PathVariable("orderStatus") String orderStatus) {
		try {
			OrderDao orderDao = new OrderDao();
			return orderDao.updateOrderStatus(orderId, orderStatus);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return false;
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/cancelOrder/{orderId}")
	public boolean cancelOrder(@PathVariable("orderId") int orderId) {
		try {
			OrderDao orderDao = new OrderDao();
			return orderDao.cancelOrder(orderId);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return false;
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/calculateTotalPrice/{orderId}")
	public double calculateTotalPrice(@PathVariable("orderId") int orderId) {
		try {
			OrderDao orderDao = new OrderDao();
			return orderDao.calculateTotalPrice(orderId);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return 0;
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/searchOrdersByStatus/{status}")
	public List<Order> searchOrdersByStatus(@PathVariable("status") String status) {
		try {
			OrderDao orderDao = new OrderDao();
			return orderDao.searchOrdersByStatus(status);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return new ArrayList<>();
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getOrdersByDateRange")
	public List<Order> getOrdersByDateRange(@RequestParam("startDate") Date startDate,
			@RequestParam("endDate") Date endDate) {
		try {
			OrderDao orderDao = new OrderDao();
			return orderDao.getOrdersByDateRange(startDate, endDate);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return new ArrayList<>();
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getTopOrders/{limit}")
	public List<Order> getTopOrders(@PathVariable("limit") int limit) {
		try {
			OrderDao orderDao = new OrderDao();
			return orderDao.getTopOrders(limit);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return new ArrayList<>();
		}
	}
}
