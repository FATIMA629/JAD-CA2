package com.bookstore.storews.book;

import java.sql.*;
import java.util.ArrayList;
import com.bookstore.storews.dbaccess.DBConnection;

public class BookDao {
	

	public Book getBookById(String bookId) {
		Connection conn = null;
		Book book = null;
		try {
			conn = DBConnection.getConnection();

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

		public boolean createBook(Book book) throws SQLException, ClassNotFoundException{
		System.out.println("Entered createBook method");
		Connection conn = null;
		boolean created = false;

		try {
			conn = DBConnection.getConnection();
			// Print statement after getting the connection to see if this line executes
			System.out.println("Got connection");

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO books (Title, Author, GenreID, Price, Quantity, Publisher, PublishDate ,ISBN, Rating, Description, ImageLocation, Sold) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			System.out.println("insert statement done");
			pstmt.setString(1, book.getTitle());
			System.out.println("insert title done");
			pstmt.setString(2, book.getAuthor());
			System.out.println("insert author done");
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
			
			System.out.println("insert done");

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

	public ArrayList<Book> readAllBooks() {
		ArrayList<Book> books = new ArrayList<>();
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

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
	
	
	public ArrayList<Book> getFilteredBooks(String[] genreIds, double price) {
	    Connection conn = null;
	    ArrayList<Book> filteredBooks = new ArrayList<>();
	    try {
	    	conn = DBConnection.getConnection();

	        // Modify the SQL query based on your database schema and table names
	        String query = "SELECT b.*, g.GenreName FROM books b JOIN genres g ON b.GenreID = g.GenreID WHERE b.Price <= ?";

	        // Check if genre is provided
	        if (genreIds != null  && genreIds.length > 0) {
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

