package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Cart.CartDao;
import book.*;
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
		System.out.println("hello i am working 1");
		int userid = (int) session.getAttribute("userId");
		System.out.println("hello i am working 2");
		String amountStr = request.getParameter("totalAmount");
		double totalPrice = Double.parseDouble(amountStr);
		System.out.println("hello i am working 3");
		System.out.println("hello i am working 4");
		System.out.println(totalPrice);
		System.out.println("hello i am working 5");
		Address address = new Address();
		System.out.println("hello i am working 6");
		address =  (Address) session.getAttribute("selectedAddress");
		System.out.println("hello i am working 7");
		String paymentType = (String) request.getAttribute("paymentType");
		System.out.println("hello i am working 8");
		String status = (String) request.getAttribute("status");
		System.out.println("hello i am working 9");
		
		Order order = new Order();
		order.setUserId(userid);
		order.setTotalPrice(totalPrice);
		order.setOrderDate(new Date());
		order.setOrderStatus("Processing");
		order.setShippingAddress(address);
		
		System.out.println("hello i am working 10");
		OrderDao orderDao = new OrderDao();
		int orderId = orderDao.createOrder(order);
		System.out.println(orderId);
		System.out.println("hello i am working 11");
		
		
		CartDao cartDao = null;
		try {
			cartDao = new CartDao();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("hello i am working 12");
		OrderItemDao orderItemDao = new OrderItemDao();
		BookDao bookDao = new BookDao();
		List<Book> cartItems = cartDao.getAllBooksInCart(userid);
		for (Book item : cartItems) {
			int bookId =item.getBookId();
			int quantity = cartDao.getQuantity(userid, bookId);
			double price = item.getPrice();
			
			Book book = new Book();
			book.setBookId(bookId);
			book.setTitle(item.getTitle());
			book.setAuthor(item.getAuthor());	
			book.setGenreId(item.getGenreId());
			book.setPrice(price);
			book.setQuantity(item.getQuantity()-quantity);
			book.setPublisher(item.getPublisher());
			book.setPublishDate(item.getPublishDate());
			book.setIsbn(item.getIsbn());
			book.setRating(item.getRating());
			book.setDescription(item.getDescription());
			book.setImageLocation(item.getImageLocation());
			book.setSold(item.getSold()+quantity);
			
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderId(orderId);
			orderItem.setBook(book);
			orderItem.setQuantity(cartDao.getQuantity(userid, bookId));
			orderItem.setUnitPrice(price);
			
			orderItemDao.createOrderItem(orderItem);
			bookDao.updateBook(book);
			cartDao.deleteFromCart(userid, bookId);
		}
		System.out.println("hello i am working 13");
		Payment payment = new Payment();
		payment.setOrderId(orderId);
		payment.setPaymentType(paymentType);
		payment.setPaymentStatus(status);
		
		PaymentDao paymentDao = new PaymentDao();
		paymentDao.createPayment(payment);
		
		List<Book> newCartItems = cartDao.getAllBooksInCart(userid);
        session.setAttribute("cartItems", newCartItems);
        
        System.out.println("cartItems in servlet is " + cartItems);
        System.out.println("Updated cartItems in servlet is " + newCartItems);
		request.setAttribute("totalPrice", totalPrice);
		System.out.println("hello i am working 15");
		String url = "/ca1/orderDetails.jsp";
		RequestDispatcher cd = request.getRequestDispatcher(url);
        cd.forward(request, response);
		
	}

}
