package servlets;
import java.io.IOException;
import java.io.PrintWriter;

import Books.BookDao;
import Books.Book;
import Cart.Cart;
import Cart.CartDao;
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
    	
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");

//        if (action != null && action.equals("delete")) {
//            int bookId = Integer.parseInt(request.getParameter("bookId"));
////            deleteCartItem(1);
//        } else {
            HttpSession session = request.getSession();
            int bookId = Integer.parseInt(request.getParameter("id"));
            int cartQuantity = Integer.parseInt(request.getParameter("cart-quantity"));

            CartDao cartDao = null;
            try {
                cartDao = new CartDao();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            cartDao.addToCart(1, bookId, cartQuantity);

            List<Book> cartItems = cartDao.getAllBooksInCart(1);
            session.setAttribute("cartItems", cartItems);
        //}

        response.sendRedirect("ca1/cart.jsp");
    }
}