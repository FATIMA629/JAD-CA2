package user;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DBAccess.DBConnection;
import Address.*;

public class UserDao {
	public boolean isUserExist(int userId) {
		Connection conn = null;
		boolean exists = false;

		try {
			conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM users WHERE UserID = ?");
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int count = rs.getInt("count");
				exists = count > 0;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return exists;
	}

	public User loginUser(String username, String password) {
		Connection conn = null;
		User user = null;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserID(rs.getInt("UserID"));
				user.setUserName(rs.getString("UserName"));
				user.setPassword(rs.getString("Password"));
				user.setEmail(rs.getString("Email"));
				user.setRole(rs.getString("Role"));
				user.setAddress(getAddressByUserId(rs.getInt("DefaultAddressID")));
				user.setPhone(rs.getString("PhoneNumber"));
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
			conn = DBConnection.getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users");

			while (rs.next()) {
				User user = new User();
				user.setUserID(rs.getInt("UserID"));
				user.setUserName(rs.getString("UserName"));
				user.setPassword(rs.getString("Password"));
				user.setEmail(rs.getString("Email"));
				user.setRole(rs.getString("Role"));
				user.setAddress(getAddressByUserId(rs.getInt("DefaultAddressID")));
				user.setPhone(rs.getString("PhoneNumber"));

				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return users;
	}

	public boolean deleteUser(int userId) {
		Connection conn = null;
		boolean deleted = false;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE UserID = ?");
			pstmt.setInt(1, userId);

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
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE UserName = ?");
			pstmt.setString(1, username);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserID(rs.getInt("UserID"));
				user.setUserName(rs.getString("UserName"));
				user.setPassword(rs.getString("Password"));
				user.setEmail(rs.getString("Email"));
				user.setRole(rs.getString("Role"));
				user.setAddress(getAddressByUserId(rs.getInt("DefaultAddressID")));
				user.setPhone(rs.getString("PhoneNumber"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return user;
	}

	public User getUserById(int userId) {
		Connection conn = null;
		User user = null;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE UserID = ?");
			pstmt.setInt(1, userId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserID(rs.getInt("UserID"));
				user.setUserName(rs.getString("UserName"));
				user.setEmail(rs.getString("Email"));
				user.setRole(rs.getString("Role"));
				user.setAddress(getAddressByUserId(rs.getInt("DefaultAddressID")));
				user.setPhone(rs.getString("PhoneNumber"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return user;
	}
	
	public String getUserNameById(int userId) {
		Connection conn = null;
		String username = null;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT UserName FROM users WHERE UserID = ?");
			pstmt.setInt(1, userId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				username = rs.getString("UserName");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return username;
		
	}

	public User getUserByEmail(String email) {
		Connection conn = null;
		User user = null;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE Email = ?");
			pstmt.setString(1, email);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserID(rs.getInt("UserID"));
				user.setUserName(rs.getString("UserName"));
				user.setPassword(rs.getString("Password"));
				user.setEmail(rs.getString("Email"));
				user.setRole(rs.getString("Role"));
				user.setAddress(getAddressByUserId(rs.getInt("DefaultAddressID")));
				user.setPhone(rs.getString("PhoneNumber"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return user;
	}
	
	public void updateDefaultAddress(User user) {
		Connection conn = null;
         System.out.println("Entered method");
		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE users SET DefaultAddressID = ? WHERE UserID = ?");
			pstmt.setInt(1, user.getAddress().getAddressID());
			pstmt.setInt(2, user.getUserID());


			System.out.println("Error is setting prepared statement");
			pstmt.executeUpdate();
			System.out.println("Error in execute update");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}
	
	public void updateUser(User user) {
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

			// Updating the user details
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE users SET UserName = ? , Role = ? , Email = ?, DefaultAddressID = ?, PhoneNumber = ? WHERE UserID = ?");
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getRole());
			pstmt.setString(3, user.getEmail());
			pstmt.setInt(4, user.getAddress().getAddressID());
			pstmt.setString(5, user.getPhone());
			pstmt.setInt(6, user.getUserID());

			pstmt.executeUpdate();

			// Updating the address details using AddressDao
			AddressDao addressDao = new AddressDao();
			addressDao.updateAddress(user.getAddress());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}


	public void createUser(User user) {
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO users (UserName, Password, Role, Email , DefaultAddressID) VALUES (?, ?, ?, ?, ?)");
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getRole());
			pstmt.setString(4, user.getEmail());
			pstmt.setInt(5, user.getAddress().getAddressID());
			pstmt.setString(6, user.getPhone());

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
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE users SET UserName = ? , Email = ?, PhoneNumber = ?, DefaultAddressID = ?, Password = ? WHERE UserID = ?");
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPhone());
			pstmt.setInt(4, user.getAddress().getAddressID());
			pstmt.setString(5, user.getPassword());
			pstmt.setInt(6, user.getUserID());


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
			conn = DBConnection.getConnection();

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

	// admin customer inquiry and reporting
	public ArrayList<User> getUserByAddress(String userChoice, String userInput) throws SQLException {
		Connection conn = null;
		ArrayList<User> userByAddressList = new ArrayList<>();
		try {
			System.out.println("in userdao get all user by address");
			conn = DBConnection.getConnection();
			System.out.println("userChoice " + userChoice);
			Map<String, String> columnQueries = new HashMap<>();
			columnQueries.put("address",
					"SELECT * FROM users WHERE DefaultAddressID IN (SELECT AddressID FROM address WHERE Address1 LIKE ? OR Address2 LIKE ?)");
			columnQueries.put("city",
					"SELECT * FROM users WHERE DefaultAddressID IN (SELECT AddressID FROM address WHERE City LIKE ?)");
			columnQueries.put("country",
					"SELECT * FROM users WHERE DefaultAddressID IN (SELECT AddressID FROM address WHERE Country LIKE ?)");
			columnQueries.put("district",
					"SELECT * FROM users WHERE DefaultAddressID IN (SELECT AddressID FROM address WHERE District LIKE ?)");
			columnQueries.put("postal_code",
					"SELECT * FROM users WHERE DefaultAddressID IN (SELECT AddressID FROM address WHERE PostalCode LIKE ?)");
			columnQueries.put("address2",
					"SELECT * FROM users WHERE DefaultAddressID IN (SELECT AddressID FROM address WHERE Address2 LIKE ?)");
			// Add more entries to the map as needed

			String sqlStr = columnQueries.get(userChoice);
			PreparedStatement ps = conn.prepareStatement(sqlStr);

			String searchPattern = "%" + userInput + "%";
			ps.setString(1, searchPattern);
			ps.setString(2, searchPattern);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setUserID(rs.getInt("UserID"));
				user.setUserName(rs.getString("UserName"));
				user.setPassword(rs.getString("Password"));
				user.setEmail(rs.getString("Email"));
				user.setRole(rs.getString("Role"));
				user.setAddress(getAddressByUserId(rs.getInt("DefaultAddressID")));
				userByAddressList.add(user);
			}

		} catch (Exception e) {
			System.err.println("..................UserDetailsDB :" + e);
		} finally {
			conn.close();
		}
		return userByAddressList;
	}

	public ArrayList<User> getUsersByRole(String role) throws SQLException {
		Connection conn = null;
		ArrayList<User> usersList = new ArrayList<>();
		try {
			conn = DBConnection.getConnection();
			String sqlStr = "SELECT * FROM users WHERE Role = ?";
			PreparedStatement ps = conn.prepareStatement(sqlStr);
			ps.setString(1, role);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				User user = getUserFromResultSet(rs);
				usersList.add(user);
			}
		} catch (Exception e) {
			System.err.println("Error: " + e);
		} finally {
			conn.close();
		}
		return usersList;
	}

	private User getUserFromResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUserID(rs.getInt("UserID"));
		user.setUserName(rs.getString("UserName"));
		user.setPassword(rs.getString("Password"));
		user.setEmail(rs.getString("Email"));
		user.setRole(rs.getString("Role"));
		user.setAddress(getAddressByUserId(rs.getInt("DefaultAddressID")));
		return user;
	}

	private Address getAddressByUserId(int userId) throws SQLException {
		Connection conn = null;
		Address address = null;
		try {
			conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM address WHERE AddressID = ?");
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				address = new Address();
				address.setAddressID(rs.getInt("AddressID"));
				address.setUserID(rs.getInt("UserID"));
				address.setAddress1(rs.getString("Address1"));
				address.setAddress2(rs.getString("Address2"));
				address.setDistrict(rs.getString("District"));
				address.setCity(rs.getString("City"));
				address.setPostalCode(rs.getString("PostalCode"));
				address.setCountry(rs.getString("Country"));
			}
		} finally {
			// Do not close the connection here as it will be closed in the calling method
		}
		return address;
	}

	public HashMap<String, Integer> getUserCountByRole(String city) throws SQLException {
		Connection conn = null;
		HashMap<String, Integer> usersCount = new HashMap<>();
		try {
			conn = DBConnection.getConnection();
			String sqlStr = "SELECT Role, COUNT(*) as count FROM users WHERE DefaultAddressID IN (SELECT AddressID FROM address WHERE City = ?) GROUP BY Role";
			PreparedStatement ps = conn.prepareStatement(sqlStr);
			ps.setString(1, city);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String role = rs.getString("Role");
				int count = rs.getInt("count");
				usersCount.put(role, count);
			}
		} catch (Exception e) {
			System.err.println("Error: " + e);
		} finally {
			conn.close();
		}
		return usersCount;
	}
	

    public double getTotalSpendingByUserId(int userId) {
        Connection conn = null;
        double totalSpending = 0;

        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT SUM(TotalPrice) AS TotalSpending "
                       + "FROM orders "
                       + "WHERE UserID = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                totalSpending = rs.getDouble("TotalSpending");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return totalSpending;
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
