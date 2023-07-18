package com.bookstore.storews.user;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class UserDao {
	private String connURL = "jdbc:mysql://localhost/ca1?user=root&password=root&serverTimezone=UTC";

	public UserDao() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); // Load JDBC Driver
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(connURL); // Establish connection to URL
	}

	public User loginUser(String username, String password) {
		Connection conn = null;
		User user = null;

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserID(rs.getInt("userID"));
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				user.setAddress(rs.getString("address"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return user;
	}

	public List<User> getAllUsers() {
		Connection conn = null;
		List<User> users = new ArrayList<>();

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users");

			while (rs.next()) {
				User user = new User();
				user.setUserID(rs.getInt("userID"));
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				user.setAddress(rs.getString("address"));

				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return users;
	}

	public boolean deleteUser(String userId) {
		Connection conn = null;
		boolean deleted = false;

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE userID = ?");
			pstmt.setString(1, userId);

			int rowsAffected = pstmt.executeUpdate();
			deleted = (rowsAffected > 0);

		} catch (SQLException e) {
			System.out.println("Error deleting user: " + e.getMessage());
			e.printStackTrace();

		} finally {
			closeConnection(conn);
		}

		return deleted;
	}

	public User getUserByUsername(String username) {
		Connection conn = null;
		User user = null;

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE userName = ?");
			pstmt.setString(1, username);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserID(rs.getInt("userID"));
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				user.setAddress(rs.getString("address"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return user;
	}

	public User getUserById(String userId) {
		Connection conn = null;
		User user = null;

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE userID = ?");
			pstmt.setString(1, userId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserID(rs.getInt("userID"));
				user.setUserName(rs.getString("userName"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				user.setAddress(rs.getString("address"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return user;
	}

	public User getUserByEmail(String email) {
		Connection conn = null;
		User user = null;

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
			pstmt.setString(1, email);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserID(rs.getInt("userID"));
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setRole(rs.getString("role"));
				user.setAddress(rs.getString("address"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return user;
	}

	public void updateUser(User user) {
		Connection conn = null;

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE users SET UserName = ? , Role = ? , Email = ?, Address = ? WHERE userID = ?");
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getRole());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getAddress());
			pstmt.setInt(5, user.getUserID());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}

	public void createUser(User user) {
		Connection conn = null;

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO users (UserName, Password, Role, Email , address) VALUES (?, ?, ?, ?, ?)");
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getRole());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getAddress());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}

	public void userUpdateUser(User user) {
	    Connection conn = null;

	    try {
	        conn = getConnection();

	        PreparedStatement pstmt = conn.prepareStatement(
	                "UPDATE users SET UserName = ? , Email = ?, Address = ?, Password = ? WHERE userID = ?");
	        pstmt.setString(1, user.getUserName());
	        pstmt.setString(2, user.getEmail());
	        pstmt.setString(3, user.getAddress());
	        pstmt.setString(4, user.getPassword());
	        pstmt.setInt(5, user.getUserID());

	        pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection(conn);
	    }
	}

	
	public int getTotalUsers() {
		Connection conn = null;
		int totalUsers = 0;

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as userCount FROM users");

			if (rs.next()) {
				totalUsers = rs.getInt("userCount");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return totalUsers;
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
