package book;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import com.bookstore.storews.book.Book;

import DBAccess.DBConnection;

public class BookDao {
	public boolean isBookExist(int bookId) {
		Connection conn = null;
		boolean exists = false;

		try {
			conn = DBConnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM books WHERE BookID = ?");
			pstmt.setInt(1, bookId);
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

	public Book getBookById(int bookId) {
		Connection conn = null;
		Book book = null;
		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books WHERE BookID = ?");
			pstmt.setInt(1, bookId);
			ResultSet rs = pstmt.executeQuery();

			// Step 7: Process Result
			if (rs.next()) {
				book = new Book();
				book.setBookId(rs.getInt("BookID"));
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
	
	public boolean createBook(Book book) throws SQLException, ClassNotFoundException, IOException {
		System.out.println("Entered createBook method");
		Connection conn = null;
		boolean created = false;

		try {
			System.out.println("Attempting to connect to the database");
			conn = DBConnection.getConnection();
			System.out.println("Connection successful");

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO books (Title, Author, GenreID, Price, Quantity, Publisher, PublishDate ,ISBN, Rating, Description, ImageLocation, Sold) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			System.out.println("Setting parameters for prepared statement");
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
			System.out.println("Parameters set");

			System.out.println("Executing update");
			int rowsAffected = pstmt.executeUpdate();

			created = (rowsAffected > 0);
			System.out.println("Insert executed, created = " + created);
		} catch (SQLException e) {
			System.out.println("Exception caught during execution");
			e.printStackTrace();
		} finally {
			System.out.println("Closing connection");
			closeConnection(conn);
			System.out.println("Connection closed");
		}

		return created;
	}

	public ArrayList<Book> readAllBooks() {
		ArrayList<Book> books = new ArrayList<>();
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM books");

			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt("BookID"));
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
			conn = DBConnection.getConnection();

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
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books WHERE ISBN = ?");
			pstmt.setString(1, isbn);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				book = new Book();
				book.setBookId(rs.getInt("BookID"));
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
			conn = DBConnection.getConnection();

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
			pstmt.setInt(13, book.getBookId());

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

	public ArrayList<Book> getTopSellingBooks(int limit) {
		Connection conn = null;
		ArrayList<Book> topSellingBooks = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books ORDER BY sold DESC LIMIT ?");
			pstmt.setInt(1, limit);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt("bookId"));
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
	
	
	public ArrayList<Book> getWorstSellingBooks(int limit) {
	    Connection conn = null;
	    ArrayList<Book> worstSellingBooks = new ArrayList<>();

	    try {
	        conn = DBConnection.getConnection();

	        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books ORDER BY sold ASC LIMIT ?");
	        pstmt.setInt(1, limit);

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            Book book = new Book();
	            book.setBookId(rs.getInt("bookId"));
	            book.setTitle(rs.getString("title"));
	            book.setAuthor(rs.getString("author"));
	            book.setSold(rs.getInt("sold"));

	            worstSellingBooks.add(book);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConnection(conn);
	    }
	    return worstSellingBooks;
	}


	public ArrayList<Book> getNewestBooks(int limit) {
		Connection conn = null;
		ArrayList<Book> newestBooks = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books ORDER BY publishDate DESC LIMIT ?");
			pstmt.setInt(1, limit);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt("bookId"));
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

	public ArrayList<Book> getHighestRatedBooks(int limit) {
		Connection conn = null;
		ArrayList<Book> highestRatedBooks = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books ORDER BY rating DESC LIMIT ?");
			pstmt.setInt(1, limit);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt("bookId"));
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
			conn = DBConnection.getConnection();

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
			conn = DBConnection.getConnection();

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
			conn = DBConnection.getConnection();

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
			conn = DBConnection.getConnection();

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

	public ArrayList<Book> getLowestStockBooks(int limit) {
		Connection conn = null;
		ArrayList<Book> lowestStockBooks = new ArrayList<>();

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books ORDER BY Quantity ASC LIMIT ?");
			pstmt.setInt(1, limit);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt("BookID"));
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
			conn = DBConnection.getConnection();

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

	public ArrayList<Book> searchBooks(String keyword) {
		Connection conn = null;
		ArrayList<Book> books = new ArrayList<>();
		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books WHERE Title LIKE ?");
			pstmt.setString(1, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();

			// Process Result
			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt("BookID"));
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

	public ArrayList<Book> getFilteredBooks(String[] genreIds, double price) {
		Connection conn = null;
		ArrayList<Book> filteredBooks = new ArrayList<>();
		try {
			conn = DBConnection.getConnection();

			// Modify the SQL query based on your database schema and table names
			String query = "SELECT b.*, g.GenreName FROM books b JOIN genres g ON b.GenreID = g.GenreID WHERE b.Price <= ?";

			// Check if genre is provided
			if (genreIds != null && genreIds.length > 0) {
				query += "AND g.GenreID IN(";
				for (int i = 0; i < genreIds.length; i++) {
					if (i > 0) {
						query += ",";
					}
					query += "?";
				}
				query += ")";
			}

			System.out.print(query);

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setDouble(1, price);

			if (genreIds != null && genreIds.length > 0) {
				for (int i = 0; i < genreIds.length; i++) {
					pstmt.setString(i + 2, genreIds[i]);
				}
			}

			ResultSet rs = pstmt.executeQuery();

			// Process the result
			while (rs.next()) {
				// Create a Book object and set its properties based on the result set
				Book book = new Book();
				book.setBookId(rs.getInt("BookID"));
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

				filteredBooks.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return filteredBooks;
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
