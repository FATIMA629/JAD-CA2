package order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Address.*;
import book.*;
import DBAccess.DBConnection;

public class OrderItemDao {

    public boolean createOrderItem(OrderItem orderItem) {
        Connection conn = null;
        boolean created = false;

        try {
            conn = DBConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO orderitems (OrderID, BookID, Quantity, UnitPrice) VALUES (?, ?, ?, ?)");

            pstmt.setInt(1, orderItem.getOrderId());
            pstmt.setInt(2, orderItem.getBook().getBookId());
            pstmt.setInt(3, orderItem.getQuantity());
            pstmt.setDouble(4, orderItem.getUnitPrice());

            int rowsAffected = pstmt.executeUpdate();
            created = (rowsAffected > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return created;
    }

    public boolean updateOrderItem(OrderItem orderItem) {
        Connection conn = null;
        boolean updated = false;

        try {
            conn = DBConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE orderitems SET BookID = ?, Quantity = ?, UnitPrice = ? WHERE OrderItemID = ?");

            pstmt.setInt(1, orderItem.getBook().getBookId());
            pstmt.setInt(2, orderItem.getQuantity());
            pstmt.setDouble(3, orderItem.getUnitPrice());
            pstmt.setInt(4, orderItem.getOrderItemId());

            int rowsAffected = pstmt.executeUpdate();
            updated = (rowsAffected > 0);

            // Logging statements
            if (updated) {
                System.out.println("OrderItem with ID " + orderItem.getOrderItemId() + " updated successfully.");
            } else {
                System.out.println("Failed to update order item with ID " + orderItem.getOrderItemId());
            }

        } catch (SQLException e) {
            System.err.println("Error updating order item with ID " + orderItem.getOrderItemId());
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return updated;
    }


    public boolean deleteOrderItem(int orderItemId) {
        Connection conn = null;
        boolean deleted = false;

        try {
            conn = DBConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM orderitems WHERE OrderItemID = ?");
            pstmt.setInt(1, orderItemId);

            int rowsAffected = pstmt.executeUpdate();
            deleted = (rowsAffected > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return deleted;
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM orderitems WHERE OrderID = ?");
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemId(rs.getInt("OrderItemID"));
                orderItem.setOrderId(rs.getInt("OrderID"));
                int bookId = rs.getInt("BookID");
                BookDao bookDao = new BookDao();
                Book book = bookDao.getBookById(bookId);
                orderItem.setBook(book);
                orderItem.setQuantity(rs.getInt("Quantity"));
                orderItem.setUnitPrice(rs.getDouble("UnitPrice"));

                orderItems.add(orderItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return orderItems;
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