package com.myshop.userws.dbaccess;

import java.sql.*;
import java.util.ArrayList;
/**
 * The UserDAO class handles database operations related to the User entity.
 */
public class UserDAO {

	/**
	 * Fetches user details from the database.
	 * 
	 * @param userid The user ID to look up.
	 * @return A User object containing the user's details, or null if the user was
	 *         not found.
	 * @throws SQLException
	 */
	public User getUserDetails(String userid) throws SQLException {
		User uBean = null;
		Connection conn = null;
		try {
			// Establish connection
			conn = DBConnection.getConnection();

			// Prepare SQL statement
			String sqlStr = "SELECT * FROM user_details WHERE userid = ?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, userid);

			// Execute query and process result
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				uBean = new User();
				uBean.setUserid(rs.getString("userid"));
				uBean.setAge(rs.getInt("age"));
				uBean.setGender(rs.getString("gender"));

				// Logging successful data retrieval
				System.out.print("....done writing to bean!...");
			}
		} catch (Exception e) {
			// Log any exceptions
			System.out.print("..........UserDetails: " + e);
		} finally {
			// Ensure connection is closed
			conn.close();
		}
		return uBean;
	}

	/**
	 * Inserts a new user into the database.
	 * 
	 * @param userid The user ID of the new user.
	 * @param age    The age of the new user.
	 * @param gender The gender of the new user.
	 * @return The number of rows affected by the insertion.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int insertUser(String userid, int age, String gender) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		int nrow = 0;
		try {
			// Establish connection
			conn = DBConnection.getConnection();

			// Prepare SQL statement
			String sqlStr = "INSERT INTO user_details VALUES (?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, userid);
			pstmt.setInt(2, age);
			pstmt.setString(3, gender);

			// Execute update and get affected row count
			nrow = pstmt.executeUpdate();
		} catch (SQLException e) {
			// Log any exceptions
			e.printStackTrace();
		}
		return nrow;
	}

	/**
	 * Checks if a user exists in the database.
	 * 
	 * @param userid The user ID to check.
	 * @return true if the user exists, false otherwise.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean userExists(String userid) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		boolean exists = false;
		try {
			// Establish connection
			conn = DBConnection.getConnection();

			// Prepare SQL statement
			String sqlStr = "SELECT * FROM user_details WHERE userid = ?";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, userid);

			// Execute query and process result
			ResultSet rs = pstmt.executeQuery();
			exists = rs.next();
		} catch (SQLException e) {
			// Log any exceptions
			e.printStackTrace();
		}
		return exists;
	}

	/**
	 * Fetches a list of all users from the database.
	 *
	 * @return An ArrayList containing User objects representing all users.
	 * @throws SQLException
	 */
	public ArrayList<User> listAllUsers() throws SQLException {
		ArrayList<User> userList = new ArrayList<>();
		Connection conn = null;
		try {
			// Establish connection
			conn = DBConnection.getConnection();

			// Prepare SQL statement
			String sqlStr = "SELECT * FROM user_details";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);

			// Execute query and process result
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserid(rs.getString("userid"));
				user.setAge(rs.getInt("age"));
				user.setGender(rs.getString("gender"));

				userList.remove(user);
			}
		} catch (Exception e) {
			// Log any exceptions
			System.out.println("..........Error fetching all users: " + e);
		} finally {
			// Ensure connection is closed
			conn.close();
		}
		return userList;
	}
	
	/**
	 * Updates an existing user in the database.
	 *
	 * @param userid The user ID of the user to be updated.
	 * @param user   The User object containing updated details.
	 * @return The number of rows affected by the update.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int updateUser(String userid, User user) throws SQLException, ClassNotFoundException {
	    int nrow = 0;
	    Connection conn = null;
	    try {
	        // Establish connection
	        conn = DBConnection.getConnection();

	        // Prepare SQL statement
	        String sqlStr = "UPDATE user_details SET age = ?, gender = ? WHERE userid = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
	        pstmt.setInt(1, user.getAge());
	        pstmt.setString(2, user.getGender());
	        pstmt.setString(3, userid);

	        // Execute update and get affected row count
	        nrow = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        // Log any exceptions
	        e.printStackTrace();
	    } finally {
	        // Ensure connection is closed
	        if (conn != null) {
	            conn.close();
	        }
	    }
	    return nrow;
	}
	
	/**
	 * Deletes a user from the database.
	 *
	 * @param userid The user ID of the user to be deleted.
	 * @return The number of rows affected by the deletion.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int deleteUser(String userid) throws SQLException, ClassNotFoundException {
	    int nrow = 0;
	    Connection conn = null;
	    try {
	        // Establish connection
	        conn = DBConnection.getConnection();

	        // Prepare SQL statement
	        String sqlStr = "DELETE FROM user_details WHERE userid = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sqlStr);
	        pstmt.setString(1, userid);

	        // Execute delete and get affected row count
	        nrow = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        // Log any exceptions
	        e.printStackTrace();
	    } finally {
	        // Ensure connection is closed
	        if (conn != null) {
	            conn.close();
	        }
	    }
	    return nrow;
	}


}
