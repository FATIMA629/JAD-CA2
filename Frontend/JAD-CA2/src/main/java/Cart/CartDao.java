package Cart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import Books.Book;
import Books.BookDao;

public class CartDao {
    private String connURL = "jdbc:mysql://localhost/ca1?user=root&password=Jer2k0518222&serverTimezone=UTC";

    public CartDao() throws ClassNotFoundException {
        // Load JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver"); // Updated JDBC driver class
    }

    public Connection getConnection() throws SQLException {
        // Establish connection to URL
        return DriverManager.getConnection(connURL);
    }

    public List<Cart> getAllCartItems() {
        Connection conn = null;
        List<Cart> carts = new ArrayList<>();
        try {
            conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM shoppingcart");
            ResultSet rs = pstmt.executeQuery();

            // Step 7: Process Result
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setuserid(rs.getInt("userid"));
                cart.setbookid(rs.getInt("bookid"));
                cart.setquantity(rs.getInt("quantity"));

                carts.add(cart);
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

        return carts;
    }

    public void addToCart(int userId, int bookId, int quantity) {
        Connection conn = null;
        try {
            conn = getConnection();

            // Check if the book already exists in the cart for the user
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM shoppingcart WHERE UserID = ? AND BookID = ?");
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, bookId);
            ResultSet checkResult = checkStmt.executeQuery();

            if (checkResult.next()) {
                // Book already exists in the cart, update the quantity
                int currentQuantity = checkResult.getInt("Quantity");
                quantity += currentQuantity;

                PreparedStatement updateStmt = conn.prepareStatement("UPDATE shoppingcart SET Quantity = ? WHERE UserID = ? AND BookID = ?");
                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, userId);
                updateStmt.setInt(3, bookId);
                updateStmt.executeUpdate();
            } else {
                // Book doesn't exist in the cart, insert a new row
                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO shoppingcart (UserID, BookID, Quantity) VALUES (?, ?, ?)");
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, bookId);
                insertStmt.setInt(3, quantity);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Book> getAllBooksInCart(int userId) {
        Connection conn = null;
        List<Book> books = new ArrayList<>();
        try {
            // Retrieve the carts from the database
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM shoppingcart WHERE UserID = ?");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            // Iterate over the carts and retrieve the associated books
            while (rs.next()) {
                int bookId = rs.getInt("BookID");
                // Retrieve the book from the database using the bookId
                BookDao bookDao = new BookDao(); // Create an instance of the BookDao
                Book book = bookDao.getBookById(Integer.toString(bookId)); // Retrieve the book using the BookDao

                // Add the book to the list of books
                	if (book != null) {
                    books.add(book);
                }
            }

            // Close the database resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return books;
    }

    public int getQuantity(int userId, int bookId) {
        int quantity = 0;
        Connection conn = null;
        try {
            conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement("SELECT Quantity FROM shoppingcart WHERE UserID = ? AND BookID = ?");
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                quantity = rs.getInt("Quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return quantity;
    }
}