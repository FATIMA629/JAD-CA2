package DBAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection is a utility class which handles connections to the database.
 */
public class DBConnection {

	/**
	 * Gets a connection to the database.
	 * 
	 * @return a Connection object to the database
	 */
	public static Connection getConnection() {

		// Database connection parameters
		String dbUrl = "jdbc:mysql://localhost/ca1";
		String dbUser = "root";
		String dbPassword = "root";
		String dbClass = "com.mysql.cj.jdbc.Driver";

		// Initialize connection object to null
		Connection connection = null;

		// Try to load the database driver class
		try {
			Class.forName(dbClass);

		} catch (ClassNotFoundException e) {
			// If the driver class was not found, print stack trace
			e.printStackTrace();

		}

		// Try to establish a connection to the database
		try {
			connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

		} catch (SQLException e) {
			// If a SQL error occurred while trying to connect, print stack trace
			e.printStackTrace();

		}

		// Return the database connection object
		return connection;
	}
}
