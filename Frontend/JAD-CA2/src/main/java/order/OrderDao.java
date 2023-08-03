package com.bookstore.storews.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.bookstore.storews.dbaccess.DBConnection;
import com.bookstore.storews.address.*;

public class OrderDao {

	public Order getOrderById(int orderId) {
		Connection conn = null;
		Order order = null;
		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orders WHERE OrderID = ?");
			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				order = mapResultSetToOrder(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return order;
	}

	public int createOrder(Order order) {
		Connection conn = null;
		int generatedOrderId = -1; // Initialize to a default value

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO orders (UserID, TotalPrice, OrderDate, OrderStatus, ShippingAddressID) VALUES (?, ?, ?, ?, ?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, order.getUserId());
			pstmt.setDouble(2, order.getTotalPrice());
			pstmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
			pstmt.setString(4, order.getOrderStatus());
			pstmt.setInt(5, order.getShippingAddress().getAddressID());

			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				// Retrieve the generated order ID
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					generatedOrderId = rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return generatedOrderId;
	}

	public List<Order> getAllOrders() {
		List<Order> orders = new ArrayList<>();
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orders");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Order order = mapResultSetToOrder(rs);
				orders.add(order);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return orders;
	}

	public boolean updateOrder(Order order) {
		Connection conn = null;
		boolean updated = false;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE orders SET UserID = ?, TotalPrice = ?, OrderDate = ?, OrderStatus = ?, ShippingAddressID = ? WHERE OrderID = ?");

			pstmt.setInt(1, order.getUserId());
			pstmt.setDouble(2, order.getTotalPrice());
			pstmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
			pstmt.setString(4, order.getOrderStatus());
			pstmt.setInt(5, order.getShippingAddress().getAddressID());
			pstmt.setInt(6, order.getOrderId());

			int rowsAffected = pstmt.executeUpdate();
			updated = (rowsAffected > 0);

			// Logging statements
			if (updated) {
				System.out.println("Order with ID " + order.getOrderId() + " updated successfully.");
			} else {
				System.out.println("Failed to update order with ID " + order.getOrderId());
			}

		} catch (SQLException e) {
			System.err.println("Error updating order with ID " + order.getOrderId());
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return updated;
	}

	public boolean deleteOrder(int orderId) {
		Connection conn = null;
		boolean deleted = false;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM orders WHERE OrderID = ?");
			pstmt.setInt(1, orderId);

			int rowsAffected = pstmt.executeUpdate();
			deleted = (rowsAffected > 0);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return deleted;
	}

	public List<OrderItem> getOrderItemsByOrderId(int orderId) {
		List<OrderItem> orderItems = new ArrayList<>();
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orderitems WHERE OrderID = ?");
			pstmt.setInt(1, orderId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderItemId(rs.getInt("OrderItemID"));
				orderItem.setOrderId(rs.getInt("OrderID"));
				orderItem.setBookId(rs.getInt("BookID"));
				orderItem.setQuantity(rs.getInt("Quantity"));
				orderItem.setUnitPrice(rs.getDouble("UnitPrice"));

				orderItems.add(orderItem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return orderItems;
	}

	public boolean updateOrderStatus(int orderId, String orderStatus) {
		Connection conn = null;
		boolean updated = false;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("UPDATE orders SET OrderStatus = ? WHERE OrderID = ?");
			pstmt.setString(1, orderStatus);
			pstmt.setInt(2, orderId);

			int rowsAffected = pstmt.executeUpdate();
			updated = (rowsAffected > 0);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return updated;
	}

	public boolean cancelOrder(int orderId) {
		Connection conn = null;
		boolean canceled = false;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn
					.prepareStatement("UPDATE orders SET OrderStatus = 'Cancelled' WHERE OrderID = ?");
			pstmt.setInt(1, orderId);

			int rowsAffected = pstmt.executeUpdate();
			canceled = (rowsAffected > 0);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return canceled;
	}

	public double calculateTotalPrice(int orderId) {
		Connection conn = null;
		double totalPrice = 0;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT SUM(UnitPrice * Quantity) AS totalPrice FROM orderitems WHERE OrderID = ?");
			pstmt.setInt(1, orderId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				totalPrice = rs.getDouble("totalPrice");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return totalPrice;
	}

	public List<Order> searchOrdersByStatus(String status) {
		List<Order> orders = new ArrayList<>();
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orders WHERE OrderStatus = ?");
			pstmt.setString(1, status);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Order order = mapResultSetToOrder(rs);
				orders.add(order);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return orders;
	}

	public List<Order> getOrdersByDateRange(Date startDate, Date endDate) {
		List<Order> orders = new ArrayList<>();
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orders WHERE OrderDate BETWEEN ? AND ?");
			pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
			pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Order order = mapResultSetToOrder(rs);
				orders.add(order);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return orders;
	}

	public List<Order> getTopOrders(int limit) {
		List<Order> orders = new ArrayList<>();
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orders ORDER BY TotalPrice DESC LIMIT ?");
			pstmt.setInt(1, limit);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Order order = mapResultSetToOrder(rs);
				orders.add(order);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return orders;
	}

	private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setOrderId(rs.getInt("OrderID"));
		order.setUserId(rs.getInt("UserID"));
		order.setTotalPrice(rs.getDouble("TotalPrice"));
		order.setOrderDate(rs.getDate("OrderDate"));
		order.setOrderStatus(rs.getString("OrderStatus"));

		// Fetch the Shipping Address using AddressDao
		int shippingAddressId = rs.getInt("ShippingAddressID");
		AddressDao addressDao = new AddressDao();
		Address shippingAddress = addressDao.getAddressById(shippingAddressId);
		order.setShippingAddress(shippingAddress);

		// Fetch the Order Items using OrderItemDao (you might need to add OrderItemDao
		// class)
		OrderItemDao orderItemDao = new OrderItemDao();
		List<OrderItem> orderItems = orderItemDao.getOrderItemsByOrderId(order.getOrderId());
		order.setOrderItems(orderItems);

		return order;
	}

	private void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
