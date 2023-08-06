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
import Address.*;

@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	public AddUserServlet() {
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

		System.out.println("Entered doPost method in AddUserServlet");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String email = request.getParameter("email");
		String role = request.getParameter("role");
		String country = request.getParameter("country");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String district = request.getParameter("district");
		String city = request.getParameter("city");
		String postalCode = request.getParameter("postalCode");

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
		if (role == null || role.trim().isEmpty()) {
			errors.put("role", "Role must be selected");
		}
		if (country == null || country.isEmpty()) {
			errors.put("country", "Country is required");
		}
		if (address1 == null || address1.isEmpty()) {
			errors.put("address1", "Address1 is required");
		}
		if (address2 == null || address2.isEmpty()) {
			errors.put("address2", "Address2 is required");
		}
		if (district == null || district.isEmpty()) {
			errors.put("district", "District is required");
		}
		if (city == null || city.isEmpty()) {
			errors.put("city", "City is required");
		}
		if (postalCode == null || postalCode.isEmpty()) {
			errors.put("postalCode", "Postal Code is required");
		}

		if (userDao.getUserByUsername(username) != null) {
			errors.put("username", "Username already exists");
		}

		if (errors.isEmpty()) {
			User user = new User();
			user.setUserName(username);
			user.setPassword(password);
			user.setEmail(email);
			user.setRole(role);

			Address address = new Address();
			address.setCountry(country);
			address.setAddress1(address1);
			address.setAddress2(address2);
			address.setDistrict(district);
			address.setCity(city);
			address.setPostalCode(postalCode);
			user.setAddress(address);

			userDao.adminCreateUser(user);

			request.getSession().setAttribute("success", "User created successfully!");
		} else {
			Map<String, String> inputData = new HashMap<>();
			inputData.put("username", username);
			inputData.put("email", email);
		    inputData.put("password", password);
		    inputData.put("confirmPassword", confirmPassword);
			inputData.put("role", role);
			inputData.put("country", country);
			inputData.put("address1", address1);
			inputData.put("address2", address2);
			inputData.put("district", district);
			inputData.put("city", city);
			inputData.put("postalCode", postalCode);

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
