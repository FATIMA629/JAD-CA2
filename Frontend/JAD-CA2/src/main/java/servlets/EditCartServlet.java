package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Cart.*;
import book.*;
/**
 * Servlet implementation class EditCartServlet
 */
@WebServlet("/EditCartServlet")
public class EditCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int userid = (int) session.getAttribute("userId");
		String action = request.getParameter("action");
		System.out.println(action);
		int bookid = Integer.parseInt(request.getParameter("bookId"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		CartDao cartDao = null;
		try {
			 cartDao = new CartDao();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ("minus".equals(action)) { 
	        int newQuantity = cartDao.updateQuantity(quantity - 1, userid, bookid);
	        System.out.println("Updated quantity in servlet is " + newQuantity);
	        if(newQuantity == 0) {
	        	cartDao.deleteFromCart(userid, bookid);
	        	List<Book> cartItems = cartDao.getAllBooksInCart(userid);
	            session.setAttribute("cartItems", cartItems);
	        }
	    } else if ("plus".equals(action)) {
	        cartDao.updateQuantity(quantity + 1, userid, bookid);
	    }
		
		response.sendRedirect(request.getContextPath() + "/ca1/cart.jsp");
	}
	

}
