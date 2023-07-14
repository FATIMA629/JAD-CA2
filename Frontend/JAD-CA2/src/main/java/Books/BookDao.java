package Books;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import Geners.Genre;
import javax.servlet.http.HttpSession;

public class BookDao {
	private String connURL = "jdbc:mysql://localhost/ca1?user=root&password=root&serverTimezone=UTC";

	public BookDao() throws ClassNotFoundException {
		// Load JDBC Driver
		Class.forName("com.mysql.cj.jdbc.Driver"); // Updated JDBC driver class
	}

	public Connection getConnection() throws SQLException {
		// Establish connection to URL
		return DriverManager.getConnection(connURL);
	}

	public Book getBookById(String bookId) {
		Connection conn = null;
		Book book = null;
		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books WHERE BookID = ?");
			pstmt.setString(1, bookId);
			ResultSet rs = pstmt.executeQuery();

			// Step 7: Process Result
			if (rs.next()) {
				book = new Book();
				book.setBookId(rs.getString("BookID"));
				book.setTitle(rs.getString("Title"));
				book.setAuthor(rs.getString("Author"));
				book.setGenreId(rs.getInt("GenreID"));
				book.setPrice(rs.getDouble("Price"));
				book.setQuantity(rs.getInt("Quantity"));
				book.setPublisher(rs.getString("Publisher"));
				book.setPublishDate(rs.getString("PublishDate"));
				book.setIsbn(rs.getString("ISBN"));
				book.setRating(rs.getDouble("Rating"));
				book.setDescription(rs.getString("Description"));
				book.setImageLocation(rs.getString("ImageLocation"));
				book.setSold(rs.getInt("Sold"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return book;
	}

		public boolean createBook(Book book) {
		System.out.println("Entered createBook method");
		Connection conn = null;
		boolean created = false;

		try {
			conn = getConnection();
			// Print statement after getting the connection to see if this line executes
			System.out.println("Got connection");

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO books (Title, Author, GenreID, Price, Quantity, Publisher, PublishDate ,ISBN, Rating, Description, ImageLocation, Sold) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getAuthor());
			pstmt.setInt(3, book.getGenreId());
			pstmt.setDouble(4, book.getPrice());
			pstmt.setInt(5, book.getQuantity());
			pstmt.setString(6, book.getPublisher());
			pstmt.setString(7, book.getPublishDate());
			pstmt.setString(8, book.getIsbn());
			pstmt.setDouble(9, book.getRating());
			pstmt.setString(10, book.getDescription());
			pstmt.setString(11, book.getImageLocation());
			pstmt.setInt(12, book.getSold());

			int rowsAffected = pstmt.executeUpdate();
			System.out.println("Executed SQL query, rows affected: " + rowsAffected);
			created = (rowsAffected > 0);

			System.out.println("Insert executed, created = " + created);
		} catch (SQLException e) {
			// Print out any exceptions that might be thrown
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return created;
	}

	public List<Book> readAllBooks() {
		Connection conn = null;
		List<Book> books = new ArrayList<>();

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM books");

			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getString("BookID"));
				book.setTitle(rs.getString("Title"));
				book.setAuthor(rs.getString("Author"));
				book.setGenreId(rs.getInt("GenreID"));
				book.setPrice(rs.getDouble("Price"));
				book.setQuantity(rs.getInt("Quantity"));
				book.setPublisher(rs.getString("Publisher"));
				book.setPublishDate(rs.getString("PublishDate"));
				book.setIsbn(rs.getString("ISBN"));
				book.setRating(rs.getDouble("Rating"));
				book.setDescription(rs.getString("Description"));
				book.setImageLocation(rs.getString("ImageLocation"));
				book.setSold(rs.getInt("Sold"));

				books.add(book);
			}

		} catch (SQLException e) {
			System.out.println("Error executing readAllBooks query: " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return books;
	}

	public boolean deleteBook(String bookId) {
		Connection conn = null;
		boolean deleted = false;

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM books WHERE BookID = ?");
			pstmt.setString(1, bookId);

			int rowsAffected = pstmt.executeUpdate();
			deleted = (rowsAffected > 0);

		} catch (SQLException e) {
			System.out.println("Error deleting book: " + e.getMessage());
			e.printStackTrace();

		} finally {
			closeConnection(conn);
		}

		return deleted;
	}

	public Book getBookByIsbn(String isbn) {
		Connection conn = null;
		Book book = null;
		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books WHERE ISBN = ?");
			pstmt.setString(1, isbn);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				book = new Book();
				book.setBookId(rs.getString("BookID"));
				book.setTitle(rs.getString("Title"));
				book.setAuthor(rs.getString("Author"));
				book.setGenreId(rs.getInt("GenreID"));
				book.setPrice(rs.getDouble("Price"));
				book.setQuantity(rs.getInt("Quantity"));
				book.setPublisher(rs.getString("Publisher"));
				book.setPublishDate(rs.getString("PublishDate"));
				book.setIsbn(rs.getString("ISBN"));
				book.setRating(rs.getDouble("Rating"));
				book.setDescription(rs.getString("Description"));
				book.setImageLocation(rs.getString("ImageLocation"));
				book.setSold(rs.getInt("Sold"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return book;
	}

	public boolean updateBook(Book book) {
		Connection conn = null;
		boolean updated = false;

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE books SET Title = ?, Author = ?, GenreID = ?, Price = ?, Quantity = ?, Publisher = ?, PublishDate = ?, ISBN = ?, Rating = ?, Description = ?, ImageLocation = ?, Sold = ? WHERE BookID = ?");

			pstmt.setString(1, book.getTitle());
			pstmt.setString(2, book.getAuthor());
			pstmt.setInt(3, book.getGenreId());
			pstmt.setDouble(4, book.getPrice());
			pstmt.setInt(5, book.getQuantity());
			pstmt.setString(6, book.getPublisher());
			pstmt.setString(7, book.getPublishDate());
			pstmt.setString(8, book.getIsbn());
			pstmt.setDouble(9, book.getRating());
			pstmt.setString(10, book.getDescription());
			pstmt.setString(11, book.getImageLocation());
			pstmt.setInt(12, book.getSold());
			pstmt.setString(13, book.getBookId());

			int rowsAffected = pstmt.executeUpdate();
			updated = (rowsAffected > 0);

		} catch (SQLException e) {
			System.out.println("Error updating book: " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return updated;
	}

	public List<Book> getTopSellingBooks(int limit) {
		Connection conn = null;
		List<Book> topSellingBooks = new ArrayList<>();

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books ORDER BY sold DESC LIMIT ?");
			pstmt.setInt(1, limit);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getString("bookId"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setSold(rs.getInt("sold"));

				topSellingBooks.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return topSellingBooks;
	}

	public List<Book> getNewestBooks(int limit) {
		Connection conn = null;
		List<Book> newestBooks = new ArrayList<>();

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books ORDER BY publishDate DESC LIMIT ?");
			pstmt.setInt(1, limit);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getString("bookId"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				// Set other properties as needed
				newestBooks.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return newestBooks;
	}

	public List<Book> getHighestRatedBooks(int limit) {
		Connection conn = null;
		List<Book> highestRatedBooks = new ArrayList<>();

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books ORDER BY rating DESC LIMIT ?");
			pstmt.setInt(1, limit);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getString("bookId"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setRating(rs.getDouble("rating"));

				highestRatedBooks.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return highestRatedBooks;
	}

	public double getTotalRevenue() {
		Connection conn = null;
		double totalRevenue = 0;

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT SUM(price * sold) as revenue FROM books");

			if (rs.next()) {
				totalRevenue = rs.getDouble("revenue");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return totalRevenue;
	}

	public int getTotalTypeOfBooks() {
		Connection conn = null;
		int totalBooks = 0;

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM books");

			if (rs.next()) {
				totalBooks = rs.getInt("total");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return totalBooks;
	}

	public int getTotalBooks() {
		Connection conn = null;
		int totalBooks = 0;

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT SUM(Quantity) as total FROM books");

			if (rs.next()) {
				totalBooks = rs.getInt("total");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return totalBooks;
	}

	public int getTotalBooksSold() {
		Connection conn = null;
		int totalBooksSold = 0;

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT SUM(Sold) AS totalSold FROM books");

			if (rs.next()) {
				totalBooksSold = rs.getInt("totalSold");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return totalBooksSold;
	}

	public List<Book> getLowestStockBooks(int limit) {
		Connection conn = null;
		List<Book> lowestStockBooks = new ArrayList<>();

		try {
			conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books ORDER BY Quantity ASC LIMIT ?");
			pstmt.setInt(1, limit);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getString("BookID"));
				book.setTitle(rs.getString("Title"));
				book.setAuthor(rs.getString("Author"));
				book.setGenreId(rs.getInt("GenreID"));
				book.setPrice(rs.getDouble("Price"));
				book.setQuantity(rs.getInt("Quantity"));
				book.setPublisher(rs.getString("Publisher"));
				book.setPublishDate(rs.getString("PublishDate"));
				book.setIsbn(rs.getString("ISBN"));
				book.setRating(rs.getDouble("Rating"));
				book.setDescription(rs.getString("Description"));
				book.setImageLocation(rs.getString("ImageLocation"));
				book.setSold(rs.getInt("Sold"));

				lowestStockBooks.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return lowestStockBooks;
	}

	public double getAverageRatingOfAllBooks() {
		Connection conn = null;
		double averageRating = 0;

		try {
			conn = getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT AVG(rating) AS averageRating FROM books");

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

	public List<Book> searchBooks(String keyword) {
	    Connection conn = null;
	    List<Book> books = new ArrayList<>();
	    try {
	        conn = getConnection();

	        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books WHERE Title LIKE ?");
	        pstmt.setString(1, "%" + keyword + "%");
	        ResultSet rs = pstmt.executeQuery();

	        // Process Result
	        while (rs.next()) {
	            Book book = new Book();
	            book.setBookId(rs.getString("BookID"));
	            book.setTitle(rs.getString("Title"));
	            book.setAuthor(rs.getString("Author"));
	            book.setGenreId(rs.getInt("GenreID"));
	            book.setPrice(rs.getDouble("Price"));
	            book.setQuantity(rs.getInt("Quantity"));
	            book.setPublisher(rs.getString("Publisher"));
	            book.setPublishDate(rs.getString("publishDate"));
	            book.setIsbn(rs.getString("ISBN"));
	            book.setRating(rs.getDouble("Rating"));
	            book.setDescription(rs.getString("Description"));
	            book.setImageLocation(rs.getString("ImageLocation"));
	            book.setSold(rs.getInt("Sold"));

	            books.add(book);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
			closeConnection(conn);
		}
	    return books;
	}
	
	
	public List<Book> getFilteredBooks(String genre, double price) {
	    Connection conn = null;
	    List<Book> books = new ArrayList<>();
	    try {
	        conn = getConnection();

	        // Modify the SQL query based on your database schema and table names
	        String query = "SELECT b.*, g.GenreName FROM books b JOIN genres g ON b.GenreID = g.GenreID";

	        // Create a list to store the query conditions
	        List<String> conditions = new ArrayList<>();

	        // Create a list to store the query parameters
	        List<Object> params = new ArrayList<>();

	        // Check if genre is provided
	        if (genre != null && !genre.isEmpty()) {
	            conditions.add("g.GenreName = ?");
	            params.add(genre);
	        }

	        // Check if price is provided
	        if (price >= 0) {
	            conditions.add("b.Price <= ?");
	            params.add(price);
	        }

	        // Add the conditions to the query if any exists
	        if (!conditions.isEmpty()) {
	            query += " WHERE " + String.join(" AND ", conditions);
	        }

	        PreparedStatement pstmt = conn.prepareStatement(query);

	        // Set the query parameters
	        for (int i = 0; i < params.size(); i++) {
	            pstmt.setObject(i + 1, params.get(i));
	        }

	        ResultSet rs = pstmt.executeQuery();

	        // Process the result
	        while (rs.next()) {
	            // Create a Book object and set its properties based on the result set
	            Book book = new Book();
	            book.setBookId(rs.getString("BookID"));
	            book.setTitle(rs.getString("Title"));
	            book.setAuthor(rs.getString("Author"));
	            book.setGenreId(rs.getInt("GenreID"));
	            book.setPrice(rs.getDouble("Price"));
	            book.setQuantity(rs.getInt("Quantity"));
	            book.setPublisher(rs.getString("Publisher"));
	            book.setPublishDate(rs.getString("publishDate"));
	            book.setIsbn(rs.getString("ISBN"));
	            book.setRating(rs.getDouble("Rating"));
	            book.setDescription(rs.getString("Description"));
	            book.setImageLocation(rs.getString("ImageLocation"));
	            book.setSold(rs.getInt("Sold"));

	            books.add(book);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
			closeConnection(conn);
		}

	    return books;
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
