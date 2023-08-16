package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpSession;
import user.*;

@WebServlet("/UserReportingServlet")
public class UserReportingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserReportingServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		session.removeAttribute("usersByRole");
		session.removeAttribute("usersByRoleError");
		session.removeAttribute("userSpending");
		session.removeAttribute("userSpendingError");
		session.removeAttribute("usersByAddress");
		session.removeAttribute("usersByAddressError");

		System.out.println("Processing User POST request");

		UserDao userDao = new UserDao();

		// Check and set userRole to the session
		String userRole = request.getParameter("userRole");
		if (userRole != null && !userRole.isEmpty()) {
			session.setAttribute("userRole", userRole);
			System.out.println("Received userRole: " + userRole);
		}

		// Check and set userIdSpending to the session
		String userIdSpending = request.getParameter("userIdSpending");
		if (userIdSpending != null && !userIdSpending.isEmpty()) {
			session.setAttribute("userIdSpending", userIdSpending);
			System.out.println("Received userIdSpending: " + userIdSpending);
		}

		// Check and set userAddressCriteria to the session
		String userAddressCriteria = request.getParameter("userAddressCriteria");
		if (userAddressCriteria != null && !userAddressCriteria.isEmpty()) {
			session.setAttribute("userAddressCriteria", userAddressCriteria);
			System.out.println("Received userAddressCriteria: " + userAddressCriteria);
		}

		// Check and set userAddressInput to the session
		String userAddressInput = request.getParameter("userAddressInput");
		if (userAddressInput != null && !userAddressInput.isEmpty()) {
			session.setAttribute("userAddressInput", userAddressInput);
			System.out.println("Received userAddressInput: " + userAddressInput);
		}

		try {
			if (userRole != null && !userRole.isEmpty()) {
				System.out.println("Fetching Users by Role");
				List<User> usersByRole = userDao.getUsersByRole(userRole);
				System.out.println("Fetched Users by Role");
				if (usersByRole == null || usersByRole.isEmpty()) {
					System.out.println("No users found by this role");
					session.setAttribute("usersByRoleError", "No users found by this role.");
				} else {
					session.setAttribute("usersByRole", usersByRole);
				}
			}
		} catch (SQLException e) {
			System.out.println("Exception occurred while fetching users by role: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			if (userIdSpending != null && !userIdSpending.isEmpty()) {
				System.out.println("Fetching User Spending");
				Double userSpending = userDao.getTotalSpendingByUserId(Integer.parseInt(userIdSpending));
				System.out.println("Fetched User Spending");
				if (userSpending == null) {
					System.out.println("No spending found for this user");
					session.setAttribute("userSpendingError", "No spending found for this user.");
				} else {
					session.setAttribute("userSpending", userSpending);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception occurred while fetching user spending: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			if (userAddressCriteria != null && !userAddressCriteria.isEmpty() && userAddressInput != null
					&& !userAddressInput.isEmpty()) {
				System.out.println("Fetching Users by Address Criteria");
				List<User> usersByAddress = userDao.getUserByAddress(userAddressCriteria, userAddressInput);
				System.out.println("Fetched Users by Address Criteria");
				if (usersByAddress == null || usersByAddress.isEmpty()) {
					System.out.println("No users found with this address criteria");
					session.setAttribute("usersByAddressError", "No users found with this address criteria.");
				} else {
					session.setAttribute("usersByAddress", usersByAddress);
				}
			}
		} catch (SQLException e) {
			System.out.println("Exception occurred while fetching users by address criteria: " + e.getMessage());
			e.printStackTrace();
		}

		response.sendRedirect("ca1/AdminReporting&Inquiry.jsp");
		System.out.println("Redirected to User Reporting JSP");
	}
}
