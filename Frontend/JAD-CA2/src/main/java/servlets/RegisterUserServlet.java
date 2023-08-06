package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import user.*;

/**
 * Servlet implementation class RegisterUser
 */
@WebServlet("/RegisterUserServlet")
public class RegisterUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterUserServlet() {
		super();
		userDao = new UserDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		// Fetch data from request
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		// Verify username and password format
		if (!username.matches("[a-zA-Z0-9_]+") || !password.matches("[a-zA-Z0-9_]+")) {
			response.sendRedirect("ca1/userRegistration.jsp?errCode=invalidFormat");
			return;
		}

		// Check if username already exists
		User existingUser = userDao.getUserByUsername(username);
		if (existingUser != null) {
			// User already exists, redirect back with error
			response.sendRedirect("ca1/userRegistration.jsp?errCode=userExists");
			return;
		}

		// Check if email already exists
		User existingEmail = userDao.getUserByEmail(email);
		if (existingEmail != null) {
			// Email already exists, redirect back with error
			response.sendRedirect("ca1/userRegistration.jsp?errCode=emailExists");
			return;
		}

		// Create a new user
		User user = new User();
		user.setUserName(username);
		user.setPassword(password);
		user.setRole("member");	
		user.setEmail(email);
		user.setAddress(null);
		user.setPhone(phone);

		session.setAttribute("userId", user.getUserID());
		session.setAttribute("role", user.getRole());
		session.setAttribute("loggedIn", true);
		
		// Add the user to the database
		userDao.createUser(user);

		// Set a success message
		request.getSession().setAttribute("success", "Registration successful! Please log in.");

		// Redirect to login page after successful registration
		response.sendRedirect("ca1/login.jsp");
	}

}
