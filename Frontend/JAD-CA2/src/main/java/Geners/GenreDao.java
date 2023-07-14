package Geners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class GenreDao {
	private String connURL = "jdbc:mysql://localhost/ca1?user=root&password=root&serverTimezone=UTC";

	public GenreDao() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver"); // Load JDBC Driver
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(connURL); // Establish connection to URL
	}

	public Genre getGenreById(int genreId) {
		Connection conn = null;
		Genre genre = null;

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM genres WHERE genreId = ?");
			pstmt.setInt(1, genreId);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				genre = new Genre();
				genre.setGenreId(rs.getInt("genreId"));
				genre.setGenreName(rs.getString("genreName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return genre;
	}

	public Map<Genre, Integer> getPopularGenres(int limit) {
		Connection conn = null;
		Map<Genre, Integer> popularGenres = new HashMap<>();

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT genreId, COUNT(*) as genreCount FROM books GROUP BY genreId ORDER BY genreCount DESC LIMIT ?");
			pstmt.setInt(1, limit);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Genre genre = getGenreById(rs.getInt("genreId"));
				int genreCount = rs.getInt("genreCount");

				popularGenres.put(genre, genreCount);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return popularGenres;
	}

	public List<Genre> getTopRatedGenres(int limit) {
	    Connection conn = null;
	    List<Genre> topRatedGenres = new ArrayList<>();

	    try {
	        conn = getConnection();

	        PreparedStatement pstmt = conn.prepareStatement(
	                "SELECT g.genreId, g.genreName, AVG(b.rating) AS avgRating " +
	                "FROM genres g " +
	                "JOIN books b ON g.genreId = b.genreId " +
	                "GROUP BY g.genreId " +
	                "ORDER BY avgRating DESC " +
	                "LIMIT ?");
	        pstmt.setInt(1, limit);

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            Genre genre = new Genre();
	            genre.setGenreId(rs.getInt("genreId"));
	            genre.setGenreName(rs.getString("genreName"));

	            topRatedGenres.add(genre);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection(conn);
	    }

	    return topRatedGenres;
	}
	
	public String getGenreNameById(int genreId) {
		Connection conn = null;
		String genreName = null;

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT genreName FROM genres WHERE genreID = ?");
			pstmt.setInt(1, genreId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				genreName = rs.getString("genreName");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return genreName;
	}

	public List<Genre> getAllGenres() {
		Connection conn = null;
		List<Genre> genres = new ArrayList<>();

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM genres");

			while (rs.next()) {
				Genre genre = new Genre();
				genre.setGenreId(rs.getInt("GenreID"));
				genre.setGenreName(rs.getString("GenreName"));

				genres.add(genre);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return genres;
	}

	public String getGenre(int genreId) {
	    Connection conn = null;
	    String genreName = null;
	    try {
	        conn = getConnection();

	        PreparedStatement pstmt = conn.prepareStatement("SELECT GenreName FROM genres WHERE genreID = ?;");
	        pstmt.setInt(1, genreId);
	        ResultSet rs = pstmt.executeQuery();

	        // Step 7: Process Result
	        if (rs.next()) {
	            genreName = rs.getString("GenreName");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Ensure connection is closed
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return genreName;
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
