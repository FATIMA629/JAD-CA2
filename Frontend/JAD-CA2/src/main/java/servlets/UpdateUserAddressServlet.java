package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.*;
import Address.*;

/**
 * Servlet implementation class UpdateUserAddressServlet
 */
@WebServlet("/UpdateUserAddressServlet")
public class UpdateUserAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserAddressServlet() {
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
		Address address = (Address) request.getAttribute("address");
		
		System.out.println("In updateAddressServlet " + address.getAddressID());
		User user = new User();
		user.setUserID(userid);
		user.setAddress(address);
		
		UserDao userDao = new UserDao();
		userDao.updateDefaultAddress(user);
		
        response.sendRedirect(request.getContextPath() + "/ca1/checkout.jsp");
	}

}
