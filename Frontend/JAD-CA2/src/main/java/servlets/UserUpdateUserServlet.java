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
		int userId = Integer.parseInt(request.getParameter("userId"));
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String Address = request.getParameter("address");
		String address2 = request.getParameter("address2");
		String country = request.getParameter("country");
		String city = request.getParameter("city");
		String district = request.getParameter("district");
		String postalCode = request.getParameter("postalCode");
		String password = request.getParameter("password"); 
		
		System.out.println("userId: " + userId);
		System.out.println("username: " + username);
		System.out.println("email: " + email);
		System.out.println("phone: " + phone);
		System.out.println("Address: " + Address);
		System.out.println("address2: " + address2);
		System.out.println("country: " + country);
		System.out.println("city: " + city);
		System.out.println("district: " + district);
		System.out.println("postalCode: " + postalCode);
		System.out.println("password: " + password);

		System.out.println("Am I working 1");
		Map<String, String> errors = new HashMap<>();
		Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
		System.out.println("Am I working 2");
		if (username == null || username.isEmpty()) {
			errors.put("username", "Username is required");
		}
		if (email == null || email.isEmpty()) {
			errors.put("email", "Email is required");
		} else if (!emailPattern.matcher(email).matches()) {
			errors.put("email", "Invalid email format");
		}
		if (phone == null || phone.isEmpty()) {
			errors.put("phone", "Phone Number is required");
		}
		if (Address == null || Address.isEmpty()) {
			errors.put("address", "Address is required");
		}
		if (country == null || country.isEmpty()) {
			errors.put("country", "Country is required");
		}
		if (city == null || city.isEmpty()) {
			errors.put("city", "City is required");
		}
		if (district == null || district.isEmpty()) {
			errors.put("district", "District is required");
		}
		if (postalCode == null || postalCode.isEmpty()) {
			errors.put("postalCode", "Postal Code is required");
		}
		if (password == null || password.isEmpty()) { // Validation for password field
			errors.put("password", "Password is required");
		}

		User user = userDao.getUserById(userId);
		if (user == null) {
			errors.put("userId", "Invalid user ID");
		}
		System.out.println("Am I working 3");
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

		System.out.println("Am I working 4");
		System.out.print(errors);
		if (errors.isEmpty()) {
			System.out.println("Am I working 4.5");
			Address address = new Address();
			AddressDao addressDao = new AddressDao();
	        address.setAddress1(Address);
	        address.setAddress2(address2);
	        address.setCountry(country);
	        address.setCity(city);
	        address.setDistrict(district);
	        address.setPostalCode(postalCode);
	        address.setUserID(userId);
	        address.setAddressID(userDao.getAddressIdById(userId));
	
			System.out.println("Address: " + address.getAddress1());
			System.out.println("address2: " + address.getAddress2());
			System.out.println("country: " + address.getCountry());
			System.out.println("city: " + address.getCity());
			System.out.println("district: " + address.getDistrict());
			System.out.println("postalCode: " + address.getPostalCode());

	        
	        
	        
			Address newaddress = addressDao.updateAddress(address);
			System.out.println("Updated Address: " + newaddress.getAddress1());
			System.out.println("Updated address2: " + newaddress.getAddress2());
			System.out.println("Updated country: " + newaddress.getCountry());
			System.out.println("Updated city: " + newaddress.getCity());
			System.out.println("Updated district: " + newaddress.getDistrict());
			System.out.println("Updated postalCode: " + newaddress.getPostalCode());
			System.out.print("Updated Address is " + newaddress);
			
	        System.out.println("Am I working 5");
			// Update the user in the database
			user.setUserName(username);
			user.setEmail(email);
			user.setAddress(newaddress);
			user.setPassword(password);
			user.setPhone(phone);

			System.out.println("Am I working 6");
			userDao.userUpdateUser(user);

			request.getSession().setAttribute("success", "User updated successfully");
			request.getSession().removeAttribute("errors");
			request.getSession().removeAttribute("inputData");
			String url = request.getContextPath() + "/ca1/home.jsp";
			response.sendRedirect(url);
		} else {
			Map<String, String> inputData = new HashMap<>();
			inputData.put("userId", Integer.toString(userId));
			inputData.put("username", username);
			inputData.put("email", email);
			inputData.put("address1", Address);
			inputData.put("address2", address2);
			inputData.put("country", country);
			inputData.put("city", city);
			inputData.put("district", district);
			inputData.put("postalCode", postalCode);

			request.getSession().setAttribute("inputData", inputData);
			request.getSession().setAttribute("errors", errors);
			response.sendRedirect("ca1/userEditUser.jsp?id=" + userId);
		}
	}
}
