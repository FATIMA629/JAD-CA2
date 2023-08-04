package sales;

import user.User;
import order.Order;
import book.Book;
import address.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DBAccess.DBConnection;

public class SalesDao {

	private User getUserFromResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUserID(rs.getInt("userID"));
		user.setUserName(rs.getString("userName"));
		user.setPassword(rs.getString("password"));
		user.setEmail(rs.getString("email"));
		user.setRole(rs.getString("role"));

        // Fetch the Shipping Address using AddressDao
        int shippingAddressId = rs.getInt("ShippingAddressID");
        AddressDao addressDao = new AddressDao();
        Address shippingAddress = addressDao.getAddressById(shippingAddressId);
        user.setAddress(shippingAddress);
		return user;
	}

	private Order getOrderFromResultSet(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setOrderId(rs.getInt("orderId"));
		order.setUserId(rs.getInt("userId"));
		order.setTotalPrice(rs.getDouble("totalPrice"));
		order.setOrderDate(rs.getDate("orderDate"));
		order.setOrderStatus(rs.getString("orderStatus"));
		
        // Fetch the Shipping Address using AddressDao
        int shippingAddressId = rs.getInt("ShippingAddressID");
        AddressDao addressDao = new AddressDao();
        Address shippingAddress = addressDao.getAddressById(shippingAddressId);
        order.setShippingAddress(shippingAddress);
		return order;
	}

	private Book getBookFromResultSet(ResultSet rs) throws SQLException {
		Book book = new Book();
		book.setBookId(rs.getString("bookId"));
		book.setTitle(rs.getString("title"));
		book.setAuthor(rs.getString("author"));
		book.setGenreId(rs.getInt("genreId"));
		book.setPrice(rs.getDouble("price"));
		book.setQuantity(rs.getInt("quantity"));
		book.setPublisher(rs.getString("publisher"));
		book.setIsbn(rs.getString("isbn"));
		book.setRating(rs.getDouble("rating"));
		book.setDescription(rs.getString("description"));
		book.setImageLocation(rs.getString("imageLocation"));
		book.setSold(rs.getInt("sold"));
		book.setPublishDate(rs.getString("publishDate"));
		return book;
	}

	public List<Order> fetchTopOrders(int limit) throws SQLException {
		List<Order> orderRecords = new ArrayList<>();
		Connection conn = DBConnection.getConnection();

		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM orders ORDER BY totalPrice DESC LIMIT ?");
			stmt.setInt(1, limit);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				orderRecords.add(getOrderFromResultSet(rs));
			}
		} finally {
			closeConnection(conn);
		}

		return orderRecords;
	}

	public List<User> fetchTopCustomers(int limit) throws SQLException {
		List<User> customerRecords = new ArrayList<>();
		Connection conn = DBConnection.getConnection();

		try {
			String sqlQuery = "SELECT u.* FROM "
					+ "(SELECT o.userId, SUM(o.totalPrice) AS total_spending FROM orders o GROUP BY o.userId) "
					+ "AS user_purchases JOIN users u ON user_purchases.userId = u.userId ORDER BY total_spending DESC LIMIT ?";
			PreparedStatement stmt = conn.prepareStatement(sqlQuery);
			stmt.setInt(1, limit);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				customerRecords.add(getUserFromResultSet(rs));
			}
		} finally {
			closeConnection(conn);
		}

		return customerRecords;
	}

	public List<Book> fetchBookSaleByDate(String date) throws SQLException {
		List<Book> bookSales = new ArrayList<>();
		Connection conn = DBConnection.getConnection();

		try {
			String sqlQuery = "SELECT b.*, SUM(oi.quantity) AS total_quantity FROM orderItems oi JOIN orders o ON oi.orderId = o.orderId JOIN books b ON b.bookId = oi.bookId WHERE DATE(o.orderDate) = ? GROUP BY b.bookId";
			PreparedStatement stmt = conn.prepareStatement(sqlQuery);
			stmt.setString(1, date);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				bookSales.add(getBookFromResultSet(rs));
			}
		} finally {
			closeConnection(conn);
		}

		return bookSales;
	}

	public List<Book> fetchBookSaleByPeriod(String start_date, String end_date) throws SQLException {
		List<Book> bookSales = new ArrayList<>();
		Connection conn = DBConnection.getConnection();

		try {
			String sqlQuery = "SELECT b.*, SUM(oi.quantity) AS total_quantity FROM orderItems oi JOIN orders o ON oi.orderId = o.orderId JOIN books b ON b.bookId = oi.bookId WHERE DATE(o.orderDate) BETWEEN ? AND ? GROUP BY b.bookId";

			PreparedStatement stmt = conn.prepareStatement(sqlQuery);
			stmt.setString(1, start_date);
			stmt.setString(2, end_date);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				bookSales.add(getBookFromResultSet(rs));
			}
		} finally {
			closeConnection(conn);
		}

		return bookSales;
	}

	public List<Book> fetchTopBooks(int limit) throws SQLException {
		List<Book> bookRecords = new ArrayList<>();
		Connection conn = DBConnection.getConnection();

		try {
			String sqlQuery = "SELECT b.*, SUM(oi.quantity) AS total_sold FROM orderItems oi JOIN books b ON b.bookId = oi.bookId GROUP BY b.bookId ORDER BY total_sold DESC LIMIT ?";
			PreparedStatement stmt = conn.prepareStatement(sqlQuery);
			stmt.setInt(1, limit);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				bookRecords.add(getBookFromResultSet(rs));
			}
		} finally {
			closeConnection(conn);
		}

		return bookRecords;
	}

	public List<Book> fetchSalesByGenre(int genreId) throws SQLException {
		List<Book> bookRecords = new ArrayList<>();
		Connection conn = DBConnection.getConnection();

		try {
			String sqlQuery = "SELECT b.*, SUM(oi.quantity) AS total_sold FROM orderItems oi JOIN books b ON b.bookId = oi.bookId WHERE b.genreId = ? GROUP BY b.bookId ORDER BY total_sold DESC";
			PreparedStatement stmt = conn.prepareStatement(sqlQuery);
			stmt.setInt(1, genreId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				bookRecords.add(getBookFromResultSet(rs));
			}
		} finally {
			closeConnection(conn);
		}

		return bookRecords;
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
