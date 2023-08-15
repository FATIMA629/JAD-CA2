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

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	public UpdateUserServlet() {
		super();
		System.out.println("Initializing UpdateUserServlet...");
		userDao = new UserDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doGet invoked in UpdateUserServlet...");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost invoked in UpdateUserServlet...");

		request.getSession().removeAttribute("errors");
		request.getSession().removeAttribute("inputData");
		request.getSession().removeAttribute("success");

		String country = request.getParameter("country");
		System.out.println("country: " + country);
		String address1 = request.getParameter("address1");
		System.out.println("address1: " + address1);
		String address2 = request.getParameter("address2");
		System.out.println("address2: " + address2);
		String district = request.getParameter("district");
		System.out.println("district: " + district);
		String city = request.getParameter("city");
		System.out.println("city: " + city);
		String postalCode = request.getParameter("postalCode");
		System.out.println("postalCode: " + postalCode);
		String userId = request.getParameter("userId");
		System.out.println("userId: " + userId);
		String username = request.getParameter("username");
		System.out.println("username: " + username);
		String email = request.getParameter("email");
		System.out.println("email: " + email);
		String role = request.getParameter("role");
		System.out.println("role: " + role);

		System.out.println("Checking for errors in user input...");
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
		if (role == null || role.isEmpty()) {
			errors.put("role", "Role is required");
		}
		if (isNullOrEmpty(request.getParameter("country")) || isNullOrEmpty(request.getParameter("address1"))
				|| isNullOrEmpty(request.getParameter("address2")) || isNullOrEmpty(request.getParameter("district"))
				|| isNullOrEmpty(request.getParameter("city"))) {
			errors.put("address", "Country, Address1, Address2, District, City must not be null");
		}
		if (request.getParameter("postalCode") == null || !request.getParameter("postalCode").matches("\\d{6}")) {
			errors.put("postalCode", "Postal code must be 6 digits");
		}

		User user = userDao.getUserById(Integer.parseInt(userId));
		if (user == null) {
			System.out.println("User not found for ID: " + userId);
			errors.put("userId", "Invalid user ID");
		}

		System.out.println("Checking for duplicate username...");
		User existingUser = userDao.getUserById(Integer.parseInt(userId));
		if (existingUser != null && !existingUser.getUserName().equals(username)) {
			User duplicateUser = userDao.getUserByUsername(username);
			if (duplicateUser != null) {
				errors.put("username", "Username already exists");
			}
		}

		System.out.println("Checking for duplicate email...");
		if (existingUser != null && !existingUser.getEmail().equals(email)) {
			User duplicateUser = userDao.getUserByEmail(email);
			if (duplicateUser != null) {
				errors.put("email", "Email already exists");
			}
		}

		if (errors.isEmpty()) {
			System.out.println("No errors detected. Proceeding with user updates...");

			System.out.println("Setting UserName: " + username);
			user.setUserName(username);

			System.out.println("Setting Email: " + email);
			user.setEmail(email);

			System.out.println("Setting Role: " + role);
			user.setRole(role);

			Address address = user.getAddress();
			if (address == null) {
				System.out.println("No existing address associated with user. Creating a new address.");
				address = new Address();
				address.setUserID(user.getUserID());
				user.setAddress(address);
			} else {
				System.out.println("User has an existing address. Updating the address details.");
			}

			System.out.println("Setting Country: " + request.getParameter("country"));
			address.setCountry(request.getParameter("country"));

			System.out.println("Setting Address1: " + request.getParameter("address1"));
			address.setAddress1(request.getParameter("address1"));

			System.out.println("Setting Address2: " + request.getParameter("address2"));
			address.setAddress2(request.getParameter("address2"));

			System.out.println("Setting District: " + request.getParameter("district"));
			address.setDistrict(request.getParameter("district"));

			System.out.println("Setting City: " + request.getParameter("city"));
			address.setCity(request.getParameter("city"));

			System.out.println("Setting PostalCode: " + request.getParameter("postalCode"));
			address.setPostalCode(request.getParameter("postalCode"));

			System.out.println("Invoking userDao to update user and associated address in the database...");
			userDao.adminUpdateUser(user);
			System.out.println("User and address update complete.");

			System.out.println("Setting success attribute in session and redirecting to adminDashboard.jsp...");
			request.getSession().setAttribute("success", "User updated successfully");
			request.getSession().removeAttribute("errors");
			request.getSession().removeAttribute("inputData");
			response.sendRedirect("ca1/adminDashboard.jsp");
		} else {
			System.out.println("Errors found. Storing the error details and input data in session.");

			System.out.println("Storing userId: " + userId);
			System.out.println("Storing username: " + username);
			System.out.println("Storing email: " + email);
			System.out.println("Storing role: " + role);

			Map<String, String> inputData = new HashMap<>();
			inputData.put("userId", userId);
			inputData.put("username", username);
			inputData.put("email", email);
			inputData.put("role", role);

			request.getSession().setAttribute("inputData", inputData);
			request.getSession().setAttribute("errors", errors);

			System.out.println("Redirecting to editUser.jsp with user ID: " + userId);
			response.sendRedirect("ca1/editUser.jsp?id=" + userId);
		}
	}

	private boolean isNullOrEmpty(String str) {
		System.out.println("Checking if string is null or empty: " + str);
		return str == null || str.isEmpty();
	}
}