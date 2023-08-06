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
import java.util.ArrayList;
import java.util.List;


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
            cartDao.addToCart(userid, bookId, cartQuantity);

            List<Book> cartItems = cartDao.getAllBooksInCart(userid);
            session.setAttribute("cartItems", cartItems);
        //}

        response.sendRedirect("ca1/cart.jsp");
            }
    }
}