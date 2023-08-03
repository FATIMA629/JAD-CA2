package Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBAccess.DBConnection;



public class AddressDao {
	public ArrayList<Address> getAddressByUserId(int userid) {
		ArrayList<Address> addressList = new ArrayList<>();
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM address WHERE userid = ?");
			pstmt.setInt(1, userid);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Address address = new Address();
				address.setAddressId(rs.getString("address_id"));
				address.setAddress(rs.getString("address"));
				address.setAddress2(rs.getString("address2"));
				address.setDistrict(rs.getString("district"));
				address.setCityId(rs.getInt("city_id"));
				address.setPostalCode(rs.getInt("postal_code"));
				address.setPhone(rs.getInt("phone"));
				address.setUserId(rs.getInt("userid"));

				addressList.add(address);
			}

		} catch (SQLException e) {
			System.out.println("Error executing getAddressByUserId query: " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return addressList;
	}
	
	
	
	public boolean createAddress(Address address) throws SQLException, ClassNotFoundException{
		System.out.println("Entered createAddress method");
		Connection conn = null;
		boolean created = false;

		try {
			conn = DBConnection.getConnection();
			// Print statement after getting the connection to see if this line executes
			System.out.println("Got connection");

			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO address(address, address2, district, city_id, postal_code, phone, userid) VALUES (?, ?, ?, ?, ?, ?, ?)");

			System.out.println("insert statement done");
			pstmt.setString(1, address.getAddress());
			pstmt.setString(2, address.getAddress2());
			pstmt.setString(3, address.getDistrict());
			pstmt.setInt(4, address	.getCityId());
			pstmt.setInt(5, address.getPostalCode());
			pstmt.setInt(6, address.getPhone());
			pstmt.setInt(7, address.getUserId());
		
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
	
	public boolean updateAddress(Address address) {
		Connection conn = null;
		boolean updated = false;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE address SET address = ?, address2 = ?, district = ?, city_id = ?, postal_code = ?, phone = ? WHERE  address_id = ?");

			pstmt.setString(1, address.getAddress());
			pstmt.setString(2, address.getAddress2());
			pstmt.setString(3, address.getDistrict());
			pstmt.setInt(4, address.getCityId());
			pstmt.setInt(5, address.getPostalCode());
			pstmt.setInt(6, address.getPhone());
			pstmt.setString(7, address.getAddressId());
			
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
	
	public boolean deleteAddress(String addressId) {
		Connection conn = null;
		boolean deleted = false;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM address WHERE address_id = ?");
			pstmt.setString(1, addressId);

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
