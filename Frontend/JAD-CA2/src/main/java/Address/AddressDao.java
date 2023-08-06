package Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DBAccess.DBConnection;

public class AddressDao {
    public Address getAddressById(int addressId) {
        Connection conn = null;
        Address address = null;

        try {
            conn = DBConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM address WHERE AddressID = ?");
            pstmt.setInt(1, addressId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                address = new Address();
                address.setAddressID(rs.getInt("AddressID"));
                address.setUserID(rs.getInt("UserID"));
                address.setAddress1(rs.getString("Address1"));
                address.setAddress2(rs.getString("Address2"));
                address.setDistrict(rs.getString("District"));
                address.setCity(rs.getString("City"));
                address.setPostalCode(rs.getString("PostalCode"));
                address.setCountry(rs.getString("Country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return address;
    }
    
    
    
    
    public List<Address> getAddressByUserId(int userId) {
        Connection conn = null;
        List<Address> addressList = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM address WHERE UserID = ?");
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Address address = new Address();
                address.setAddressID(rs.getInt("AddressID"));
                address.setUserID(rs.getInt("UserID"));
                address.setAddress1(rs.getString("Address1"));
                address.setAddress2(rs.getString("Address2"));
                address.setDistrict(rs.getString("District"));
                address.setCity(rs.getString("City"));
                address.setPostalCode(rs.getString("PostalCode"));
                address.setCountry(rs.getString("Country"));
                
                addressList.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return addressList;
    }

    public List<Address> getAllAddresses() {
        Connection conn = null;
        List<Address> addresses = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM address");

            while (rs.next()) {
                Address address = new Address();
                address.setAddressID(rs.getInt("AddressID"));
                address.setUserID(rs.getInt("UserID"));
                address.setAddress1(rs.getString("Address1"));
                address.setAddress2(rs.getString("Address2"));
                address.setDistrict(rs.getString("District"));
                address.setCity(rs.getString("City"));
                address.setPostalCode(rs.getString("PostalCode"));
                address.setCountry(rs.getString("Country"));

                addresses.add(address);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return addresses;
    }

	    public Address createAddress(Address address) {
	        Connection conn = null;
	
	        try {
	            conn = DBConnection.getConnection();
	
	            PreparedStatement pstmt = conn.prepareStatement(
	                "INSERT INTO address (UserID, Address1, Address2, District, City, PostalCode, Country) VALUES (?, ?, ?, ?, ?, ?, ?)",
	                Statement.RETURN_GENERATED_KEYS); // Requesting generated keys
	
	            pstmt.setInt(1, address.getUserID());
	            pstmt.setString(2, address.getAddress1());
	            pstmt.setString(3, address.getAddress2());
	            pstmt.setString(4, address.getDistrict());
	            pstmt.setString(5, address.getCity());
	            pstmt.setString(6, address.getPostalCode());
	            pstmt.setString(7, address.getCountry());
	
	            int rowsAffected = pstmt.executeUpdate();
	
	            if (rowsAffected > 0) {
	                ResultSet generatedKeys = pstmt.getGeneratedKeys();
	                if (generatedKeys.next()) {
	                    int generatedAddressId = generatedKeys.getInt(1); // Get the generated Address ID
	                    address.setAddressID(generatedAddressId); // Set the generated Address ID in the address object
	                }
	            }
	
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            closeConnection(conn);
	        }
	
	        return address; // Return the Address object with the generated Address ID
	    }



	    public Address updateAddress(Address address) {
	        Connection conn = null;
	        Address updatedAddress = null;

	        try {
	            conn = DBConnection.getConnection();

	            System.out.println("Preparing SQL statement...");
	            PreparedStatement pstmt = conn.prepareStatement(
	                    "UPDATE address SET UserID = ?, Address1 = ?, Address2 = ?, District = ?, City = ?, PostalCode = ?, Country = ? WHERE AddressID = ?");
	            
	            System.out.println("Setting PreparedStatement parameters...");
	            pstmt.setInt(1, address.getUserID());
	            pstmt.setString(2, address.getAddress1());
	            pstmt.setString(3, address.getAddress2());
	            pstmt.setString(4, address.getDistrict());
	            pstmt.setString(5, address.getCity());
	            pstmt.setString(6, address.getPostalCode());
	            pstmt.setString(7, address.getCountry());
	            pstmt.setInt(8, address.getAddressID());

	            System.out.println("Executing update...");
	            int rowsUpdated = pstmt.executeUpdate();
	            System.out.println("Rows updated: " + rowsUpdated);

	            if (rowsUpdated > 0) {
	                updatedAddress = address; // The updated object can be considered the same as the input object
	            }

	        } catch (SQLException e) {
	            System.err.println("An SQL exception occurred:" + e);
	            e.printStackTrace();
	        } finally {
	            closeConnection(conn);
	        }

	        System.out.println("Returning updated address: " + updatedAddress);
	        return updatedAddress;
	    }



    public boolean deleteAddress(int addressID) {
        Connection conn = null;
        boolean deleted = false;

        try {
            conn = DBConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM address WHERE AddressID = ?");
            pstmt.setInt(1, addressID);

            int rowsAffected = pstmt.executeUpdate();
            deleted = (rowsAffected > 0);

        } catch (SQLException e) {
            System.out.println("Error deleting address: " + e.getMessage());
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
