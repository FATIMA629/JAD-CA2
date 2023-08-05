package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import book.*;
import Cart.*;
import user.*;

@WebServlet("/VerifyLoginServlet")
public class VerifyLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// Check username and password format
		if (!username.matches("[a-zA-Z0-9_]+") || !password.matches("[a-zA-Z0-9_]+")) {
			response.sendRedirect("ca1/login.jsp?errCode=invalidFormat");
			return;
		}

		try {
			CartDao cartDao = new CartDao();
			UserDao userDao = new UserDao();
			User user = userDao.loginUser(username, password);

			if (user != null) {
				session.setAttribute("userId", user.getUserID());
				session.setAttribute("role", user.getRole());
				session.setAttribute("username", user.getUserName());
				session.setAttribute("phone", user.getPhone());
				System.out.println("VerifyLoginServlet phone is " + user.getPhone());
				session.setAttribute("loggedIn", true);
				List<Book> cartItems = cartDao.getAllBooksInCart(user.getUserID());
				session.setAttribute("cartItems", cartItems);

				if (user.getRole().equals("admin")) {
					response.sendRedirect("ca1/adminDashboard.jsp");
				} else {
					response.sendRedirect("ca1/home.jsp");
				}
			} else {
				response.sendRedirect("ca1/login.jsp?errCode=invalidLogin");
			}
		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.println("Error: " + e.getMessage());
		}
	}
}
