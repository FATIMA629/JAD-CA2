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

import Cart.CartDao;
import book.Book;
import order.*;
import Address.*;
import Payment.*;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int userid = (int) session.getAttribute("userId");
		int amountInCents = (int) session.getAttribute("totalPrice");
		double totalPrice = (double) (amountInCents/100);
		System.out.println(totalPrice);
		Address address = new Address();
		address =  (Address) session.getAttribute("selectedAddress");
		String paymentType = (String) request.getAttribute("paymentType");
		String status = (String) request.getAttribute("status");
		
		Order order = new Order();
		order.setUserId(userid);
		order.setTotalPrice(totalPrice);
		order.setOrderDate(null);
		order.setOrderStatus(null);
		order.setShippingAddress(address);
		
	
		OrderDao orderDao = new OrderDao();
		int orderId = orderDao.createOrder(order);
		
		CartDao cartDao = null;
		try {
			cartDao = new CartDao();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		OrderItemDao orderItemDao = new OrderItemDao();
		List<Book> cartItems = cartDao.getAllBooksInCart(userid);
		for (Book item : cartItems) {
			int bookId = Integer.parseInt(item.getBookId());
			double price = item.getPrice();
			
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderId(orderId);
			orderItem.setBookId(bookId);
			orderItem.setQuantity(cartDao.getQuantity(userid, bookId));
			orderItem.setUnitPrice(price);
			
			orderItemDao.createOrderItem(orderItem);
		}
		
		Payment payment = new Payment();
		payment.setOrderId(orderId);
		payment.setPaymentType(paymentType);
		payment.setPaymentStatus(status);
	    
		
		
	}

}
