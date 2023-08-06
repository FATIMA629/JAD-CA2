package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sales.SalesDao;
import book.BookDao;
import book.Book;
import order.Order;
import user.User;

@WebServlet("/ReportingServlet")
public class ReportingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReportingServlet() {
		super();
		System.out.println("Initializing ReportingServlet");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Processing POST request");

		HttpSession session = request.getSession();
		session.removeAttribute("totalRevenue");
		session.removeAttribute("topSellingBooks");
		session.removeAttribute("topOrders");
		session.removeAttribute("topCustomers");
		session.removeAttribute("bookSalesByDate");
		session.removeAttribute("bookSalesByPeriod");
		session.removeAttribute("bookSalesByGenre");
		// Removing error messages from session
		session.removeAttribute("topSellingBooksError");
		session.removeAttribute("topOrdersError");
		session.removeAttribute("topCustomersError");
		session.removeAttribute("bookSalesByDateError");
		session.removeAttribute("bookSalesByPeriodError");
		session.removeAttribute("bookSalesByGenreError");

		SalesDao salesDao = new SalesDao();
		BookDao bookDao = new BookDao();

		String numTopSellingBooks = request.getParameter("numTopSellingBooks");
		System.out.println("Received numTopSellingBooks: " + numTopSellingBooks);

		String numTopOrders = request.getParameter("numTopOrders");
		System.out.println("Received numTopOrders: " + numTopOrders);

		String numTopCustomers = request.getParameter("numTopCustomers");
		System.out.println("Received numTopCustomers: " + numTopCustomers);

		String targetDate = request.getParameter("targetDate");
		System.out.println("Received targetDate: " + targetDate);

		String startDate = request.getParameter("startDate");
		System.out.println("Received startDate: " + startDate);

		String endDate = request.getParameter("endDate");
		System.out.println("Received endDate: " + endDate);

		String targetGenreId = request.getParameter("targetGenreId");
		System.out.println("Received targetGenreId: " + targetGenreId);

		session.setAttribute("totalRevenue", bookDao.getTotalRevenue());
		System.out.println("Set total revenue");

		try {
			if (numTopSellingBooks != null && !numTopSellingBooks.isEmpty()) {
				System.out.println("Fetching top books");
				List<Book> topBooks = salesDao.fetchTopBooks(Integer.parseInt(numTopSellingBooks));
				System.out.println("Fetched top books");
				if (topBooks == null || topBooks.isEmpty()) {
					System.out.println("No top selling books found");
					session.setAttribute("topSellingBooksError", "No top selling books found.");
				} else {
					session.setAttribute("topSellingBooks", topBooks);
				}
			}
		} catch (NumberFormatException | SQLException e) {
			System.out.println("Exception occurred while fetching top books: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			if (numTopOrders != null && !numTopOrders.isEmpty()) {
				System.out.println("Fetching top orders");
				List<Order> topOrders = salesDao.fetchTopOrders(Integer.parseInt(numTopOrders));
				System.out.println("Fetched top orders");
				if (topOrders == null || topOrders.isEmpty()) {
					System.out.println("No top orders found");
					session.setAttribute("topOrdersError", "No top orders found.");
				} else {
					session.setAttribute("topOrders", topOrders);
				}
			}
		} catch (NumberFormatException | SQLException e) {
			System.out.println("Exception occurred while fetching top orders: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			if (numTopCustomers != null && !numTopCustomers.isEmpty()) {
				System.out.println("Fetching top customers");
				List<User> topCustomers = salesDao.fetchTopCustomers(Integer.parseInt(numTopCustomers));
				System.out.println("Fetched top customers");
				if (topCustomers == null || topCustomers.isEmpty()) {
					System.out.println("No top customers found");
					session.setAttribute("topCustomersError", "No top customers found.");
				} else {
					session.setAttribute("topCustomers", topCustomers);
				}
			}
		} catch (NumberFormatException | SQLException e) {
			System.out.println("Exception occurred while fetching top customers: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			if (targetDate != null && !targetDate.isEmpty()) {
				System.out.println("Fetching book sales by date");
				List<Book> bookSalesByDate = salesDao.fetchBookSaleByDate(targetDate);
				System.out.println("Fetched book sales by date");
				if (bookSalesByDate == null || bookSalesByDate.isEmpty()) {
					System.out.println("No book sales by date found");
					session.setAttribute("bookSalesByDateError", "No book sales by date found.");
				} else {
					session.setAttribute("bookSalesByDate", bookSalesByDate);
				}
			}
		} catch (SQLException e) {
			System.out.println("Exception occurred while fetching book sales by date: " + e.getMessage());
			e.printStackTrace();
		}

		try {
			if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
				System.out.println("Fetching book sales by period");
				List<Book> bookSalesByPeriod = salesDao.fetchBookSaleByPeriod(startDate, endDate);
				System.out.println("Fetched book sales by period");
				if (bookSalesByPeriod == null || bookSalesByPeriod.isEmpty()) {
					System.out.println("No book sales by period found");
					session.setAttribute("bookSalesByPeriodError", "No book sales by period found.");
				} else {
					session.setAttribute("bookSalesByPeriod", bookSalesByPeriod);
				}
			}
		} catch (SQLException e1) {
			System.out.println("Exception occurred while fetching book sales by period: " + e1.getMessage());
			e1.printStackTrace();
		}

		try {
			if (targetGenreId != null && !targetGenreId.isEmpty()) {
				System.out.println("Fetching book sales by genre");
				List<Book> bookSalesByGenre = salesDao.fetchSalesByGenre(Integer.parseInt(targetGenreId));
				System.out.println("Fetched book sales by genre");
				if (bookSalesByGenre == null || bookSalesByGenre.isEmpty()) {
					System.out.println("No book sales by genre found");
					session.setAttribute("bookSalesByGenreError", "No book sales by genre found.");
				} else {
					session.setAttribute("bookSalesByGenre", bookSalesByGenre);
				}
			}
		} catch (NumberFormatException | SQLException e) {
			System.out.println("Exception occurred while fetching book sales by genre: " + e.getMessage());
			e.printStackTrace();
		}

		response.sendRedirect("ca1/AdminReporting&Inquiry.jsp");
		System.out.println("Redirected to JSP");
	}
}
