package servlets;
import java.io.IOException;
import java.io.PrintWriter;

import book.*;
import Cart.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import javax.servlet.http.HttpSession;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddToCartServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
        System.out.println("Total Price from addtocartservlet" + request.getParameter("amount"));
    
    	session.setAttribute("totalPrice", request.getParameter("amount"));
    	System.out.println("totalPrice");
    	
    	response.sendRedirect("ca1/checkout.jsp");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");


            HttpSession session = request.getSession();
            session.removeAttribute("errors");
            Integer userIdInteger = (Integer) session.getAttribute("userId");
            int userid = (userIdInteger != null) ? userIdInteger.intValue() : 0;
            
            if(userid == 0) {
            	
            	response.sendRedirect("ca1/login.jsp");
            } else {
            
            int bookId = Integer.parseInt(request.getParameter("id"));
            int cartQuantity = Integer.parseInt(request.getParameter("cart-quantity"));

            CartDao cartDao = null;
            try {
                cartDao = new CartDao();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            
            Map<String, String> errors = new HashMap<>();
            BookDao bookDao = new BookDao();
            Book book = bookDao.getBookById(bookId);
                     
            if(cartQuantity >  book.getQuantity()) {
            	errors.put("quantity", "Cart items added available amount");
            } 
            
            if(book.getQuantity() == 0) {
            	errors.put("quantity", "Book out of stock");
            }
            
            if (errors.isEmpty()) {
            cartDao.addToCart(userid, bookId, cartQuantity);

            List<Book> cartItems = cartDao.getAllBooksInCart(userid);
            session.setAttribute("cartItems", cartItems);
            session.removeAttribute("errors");
            response.sendRedirect("ca1/cart.jsp");
            } else {
            	session.setAttribute("errors", errors);
            	response.sendRedirect("ca1/viewBook.jsp");
            }
            
            }
    }
}