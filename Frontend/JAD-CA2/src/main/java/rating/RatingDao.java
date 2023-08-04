package rating;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RatingDao {
	private String connURL = "jdbc:mysql://localhost/ca1?user=root&password=root&serverTimezone=UTC";

	public RatingDao() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); // Load JDBC Driver
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(connURL); // Establish connection to URL
	}

	public ArrayList<Rating> getRatingsForBook(int bookId) {
		Connection conn = null;
		ArrayList<Rating> ratings = new ArrayList<>();

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ratings WHERE bookId = ?");
			pstmt.setInt(1, bookId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Rating rating = new Rating();
				rating.setRatingId(rs.getInt("ratingId"));
				rating.setBookId(rs.getInt("bookId"));
				rating.setUserId(rs.getInt("userId"));
				rating.setRating(rs.getDouble("rating"));
				rating.setHelpful(rs.getInt("helpful"));
				rating.setComment(rs.getString("comment"));

				ratings.add(rating);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return ratings;
	}

	public double getAverageRatingForBook(int bookId) {
		Connection conn = null;
		double averageRating = 0;

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn
					.prepareStatement("SELECT AVG(rating) AS averageRating FROM ratings WHERE bookId = ?");
			pstmt.setInt(1, bookId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				averageRating = rs.getDouble("averageRating");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return averageRating;
	}

	public boolean incrementHelpfulCount(int ratingId) {
		Connection conn = null;
		boolean updated = false;

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn
					.prepareStatement("UPDATE ratings SET helpful = helpful + 1 WHERE ratingId = ?");
			pstmt.setInt(1, ratingId);
			int rowsAffected = pstmt.executeUpdate();
			
            System.out.println("Executed SQL query, rows affected: " + rowsAffected);
			updated = (rowsAffected > 0);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return updated;
	}

	public boolean addRating(Rating rating) {
		Connection conn = null;
		boolean created = false;

		try {
			conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ratings (bookId, userId, rating, helpful, comment) VALUES (?, ?, ?, ?, ?)");
			pstmt.setInt(1, rating.getBookId());
			pstmt.setInt(2, rating.getUserId());
			pstmt.setDouble(3, rating.getRating());
			pstmt.setInt(4, rating.getHelpful());
			pstmt.setString(5, rating.getComment());
			int rowsAffected = pstmt.executeUpdate();

            System.out.println("Executed SQL query, rows affected: " + rowsAffected);
			created = (rowsAffected > 0);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return created;
	}

	public void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
