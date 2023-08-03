package Country;

import java.sql.*;
import java.util.ArrayList;
import DBAccess.DBConnection;


public class CountryDao {

	public ArrayList<Country> getCountry() {
		ArrayList<Country> countryList = new ArrayList<>();
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM country");

			while (rs.next()) {
				Country country = new Country();
				country.setCountryId(rs.getString("country_id"));
				country.setCountry(rs.getString("country"));
				
				countryList.add(country);
			}
			System.out.println(countryList);

		} catch (SQLException e) {
			System.out.println("Error executing getCountry query: " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return countryList;
	}
	
//	public String getCountryId() {
//		Connection conn = null;
//		String country = request.getParameter("countryId");
//
//		try {
//			conn = DBConnection.getConnection();
//
//		
//			PreparedStatement pstmt = conn.prepareStatement("SELECT country_id FROM country WHERE country = ?");
//
//			pstmt.setString(1, );
//			while (rs.next()) {
//				Country country = new Country();
//				country.setCountryId(rs.getString("country_id"));
//				country.setCountry(rs.getString("country"));
//				
//				countryList.add(country);
//			}
//
//		} catch (SQLException e) {
//			System.out.println("Error executing getCountry query: " + e.getMessage());
//			e.printStackTrace();
//		} finally {
//			closeConnection(conn);
//		}
//		
//		return countryId;
//	}
//	
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
