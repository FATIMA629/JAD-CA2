package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Cart.CartDao;
import book.Book;

/**
 * Servlet implementation class ReadAllBooksFromCart
 */
@WebServlet("/ReadAllBooksFromCart")
public class ReadFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadFromCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        int userid = (int) session.getAttribute("userId");

        CartDao cartDao = null;
        try {
            cartDao = new CartDao();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<Book> cartItems = cartDao.getAllBooksInCart(userid);
        session.setAttribute("cartItems", cartItems);

        response.sendRedirect("ca1/cart.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
