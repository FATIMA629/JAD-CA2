package City;

import java.sql.*;
import java.util.ArrayList;

import DBAccess.DBConnection;

public class CityDao {

	public ArrayList<City> getCity(int countryId) {
		ArrayList<City> cityList = new ArrayList<>();
		Connection conn = null;

		try {
			conn = DBConnection.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM city WHERE country_id = ?");
			pstmt.setInt(1, countryId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				City city = new City();
				city.setCityId(rs.getString("city_id"));
				city.setCity(rs.getString("city"));
				city.setCountryId(rs.getInt("country_id"));
				
				cityList.add(city);
			}

		} catch (SQLException e) {
			System.out.println("Error executing getCity query: " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return cityList;
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
