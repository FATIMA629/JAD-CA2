package Payment;

import java.sql.*;
import java.util.*;

import DBAccess.DBConnection;
import order.OrderItem;

public class PaymentDao {

	 public boolean createPayment(Payment payment) {
	        Connection conn = null;
	        boolean created = false;

	        try {
	            conn = DBConnection.getConnection();

	            PreparedStatement pstmt = conn.prepareStatement(
	                    "INSERT INTO payments (OrderID, PaymentType, PaymentStatus) VALUES (?, ?, ?)");

	            pstmt.setInt(1, payment.getOrderId());
	            pstmt.setString(2, payment.getPaymentType());
	            pstmt.setString(3, payment.getPaymentStatus());

	            int rowsAffected = pstmt.executeUpdate();
	            created = (rowsAffected > 0);

	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            closeConnection(conn);
	        }

	        return created;
	    }

	    public boolean updatePayment(Payment payment) {
	        Connection conn = null;
	        boolean updated = false;

	        try {
	            conn = DBConnection.getConnection();

	            PreparedStatement pstmt = conn.prepareStatement(
	                    "UPDATE payments SET OrderID = ?, PaymentType = ?, PaymentStatus = ? WHERE PaymentID = ?");

	            pstmt.setInt(1, payment.getOrderId());
	            pstmt.setString(2, payment.getPaymentType());
	            pstmt.setString(3, payment.getPaymentStatus());
	            pstmt.setInt(4, payment.getPaymentId());

	            int rowsAffected = pstmt.executeUpdate();
	            updated = (rowsAffected > 0);

	            // Logging statements
	            if (updated) {
	                System.out.println("Payment with ID " + payment.getPaymentId() + " updated successfully.");
	            } else {
	                System.out.println("Failed to update order item with ID " + payment.getPaymentId());
	            }

	        } catch (SQLException e) {
	            System.err.println("Error updating order item with ID " + payment.getPaymentId());
	            e.printStackTrace();
	        } finally {
	            closeConnection(conn);
	        }

	        return updated;
	    }


	    public boolean deletePayment(int paymentId) {
	        Connection conn = null;
	        boolean deleted = false;

	        try {
	            conn = DBConnection.getConnection();

	            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM payments WHERE PaymentID = ?");
	            pstmt.setInt(1, paymentId);

	            int rowsAffected = pstmt.executeUpdate();
	            deleted = (rowsAffected > 0);

	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            closeConnection(conn);
	        }

	        return deleted;
	    }

	    public Payment getPaymentById(int paymentId) {
	        Connection conn = null;
	        Payment payment = null;

	        try {
	            conn = DBConnection.getConnection();

	            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM payments WHERE PaymentID = ?");
	            pstmt.setInt(1, paymentId);
	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                payment = new Payment();
	                payment.setPaymentId(rs.getInt("PaymentID"));;
	                payment.setOrderId(rs.getInt("OrderID"));
	                payment.setPaymentType(rs.getString("PaymentType"));
	                payment.setPaymentStatus(rs.getString("PaymentStatus"));
	                
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            closeConnection(conn);
	        }

	        return payment;
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
