package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.*;

/**
 * Servlet implementation class AddUserServlet
 */
@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddUserServlet() {
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
		request.getSession().removeAttribute("errors");
		request.getSession().removeAttribute("inputData");
		request.getSession().removeAttribute("success");

		System.out.println("Entered doPost method in AddUserServlet");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String role = request.getParameter("role");

		Map<String, String> errors = new HashMap<>();
		if (username == null || username.isEmpty()) {
			errors.put("username", "Username is required");
		}
		if (password == null || password.isEmpty()) {
			errors.put("password", "Password is required");
		}
		if (confirmPassword == null || confirmPassword.isEmpty()) {
			errors.put("confirmPassword", "Confirm password is required");
		}
		if (!password.equals(confirmPassword)) {
			errors.put("confirmPassword", "Passwords do not match");
		}
		if (email == null || email.isEmpty()) {
			errors.put("email", "Email is required");
		} else if (!isValidEmail(email)) {
			errors.put("email", "Invalid email format");
		}
		if (address == null || address.isEmpty()) {
			errors.put("address", "Address is required");
		}
		if (role == null || role.trim().isEmpty()) {
			errors.put("role", "Role must be selected");
		}

		if (userDao.getUserByUsername(username) != null) {
			errors.put("username", "Username already exists");
		}

		if (errors.isEmpty()) {
			User user = new User();
			user.setUserName(username);
			user.setPassword(password);
			user.setEmail(email);
			user.setAddress(address);
			user.setRole(role);

			userDao.createUser(user);

			request.getSession().setAttribute("success", "User created successfully!");
		} else {
			Map<String, String> inputData = new HashMap<>();
			inputData.put("username", username);
			inputData.put("email", email);
			inputData.put("address", address);
		    inputData.put("password", password);
		    inputData.put("confirmPassword", confirmPassword);
			inputData.put("role", role);

			request.getSession().setAttribute("inputUserData", inputData);
			request.getSession().setAttribute("userErrors", errors);
		}

		response.sendRedirect(request.getContextPath() + "/ca1/adminDashboard.jsp");
	}

	private boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
	}
}
