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

@WebServlet("/UserUpdateUserServlet")
public class UserUpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	public UserUpdateUserServlet() {
		super();
		userDao = new UserDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("errors");
		request.getSession().removeAttribute("inputData");
		request.getSession().removeAttribute("success");

		System.out.println("Entered doPost method in UpdateUserServlet");
		String userId = request.getParameter("userId");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String password = request.getParameter("password"); 

		Map<String, String> errors = new HashMap<>();
		Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");

		if (username == null || username.isEmpty()) {
			errors.put("username", "Username is required");
		}
		if (email == null || email.isEmpty()) {
			errors.put("email", "Email is required");
		} else if (!emailPattern.matcher(email).matches()) {
			errors.put("email", "Invalid email format");
		}
		if (address == null || address.isEmpty()) {
			errors.put("address", "Address is required");
		}
		if (password == null || password.isEmpty()) { // Validation for password field
			errors.put("password", "Password is required");
		}

		User user = userDao.getUserById(userId);
		if (user == null) {
			errors.put("userId", "Invalid user ID");
		}

		User existingUser = userDao.getUserById(userId);

		// Check for duplicate username
		if (existingUser != null && !existingUser.getUserName().equals(username)) {
			User duplicateUser = userDao.getUserByUsername(username);
			if (duplicateUser != null) {
				errors.put("username", "Username already exists");
			}
		}

		// Check for duplicate email
		if (existingUser != null && !existingUser.getEmail().equals(email)) {
			User duplicateUser = userDao.getUserByEmail(email);
			if (duplicateUser != null) {
				errors.put("email", "Email already exists");
			}
		}

		if (errors.isEmpty()) {
			// Update the user in the database
			user.setUserName(username);
			user.setEmail(email);
			user.setAddress(address);
			user.setPassword(password);

			userDao.userUpdateUser(user);

			request.getSession().setAttribute("success", "User updated successfully");
			request.getSession().removeAttribute("errors");
			request.getSession().removeAttribute("inputData");
			response.sendRedirect("home.jsp");
		} else {
			Map<String, String> inputData = new HashMap<>();
			inputData.put("userId", userId);
			inputData.put("username", username);
			inputData.put("email", email);
			inputData.put("address", address);

			request.getSession().setAttribute("inputData", inputData);
			request.getSession().setAttribute("errors", errors);
			response.sendRedirect("ca1/userEditUser.jsp?id=" + userId);
		}
	}
}
