package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Address.*;
import book.Book;
import user.*;



/**
 * Servlet implementation class AddressServlet
 */
@WebServlet("/AddressServlet")
public class AddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddressServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String addressIdStr = request.getParameter("selectedAddress");
		Map<String, String> errors = new HashMap<>();
		HttpSession session = request.getSession();
		if(addressIdStr == null) {
			errors.put("selectedAddress", "Please select a valid address");
		}
		if (errors.isEmpty()) {
		int addressId = Integer.parseInt(request.getParameter("selectedAddress"));
		System.out.println(addressId);
		AddressDao addressDao = new AddressDao();
		Address address = new Address();
		
		
		address = addressDao.getAddressById(addressId);
		
		
		
		
		
		

            session.setAttribute("selectedAddress", address);
            session.removeAttribute("errors");
            
    		response.sendRedirect(request.getContextPath() + "/CheckoutServlet");	
            } else {
            	session.setAttribute("errors", errors);
            	response.sendRedirect(request.getContextPath() + "/ca1/checkout.jsp");
            }
		
		
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String Address = request.getParameter("address");
		String address2 = request.getParameter("address2");
		String district = request.getParameter("district");
		String country = request.getParameter("country");
		String city = request.getParameter("city");
		String postalCode = request.getParameter("postalCode");
        int userid = (int) session.getAttribute("userId");
        
        Address address = new Address();
        address.setAddress1(Address);
        address.setAddress2(address2);
        address.setDistrict(district);
        address.setCountry(country);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setUserID(userid);
		
        AddressDao addressDao = new AddressDao();
		address = addressDao.createAddress(address);
		
		User user = new User();
		user.setUserID(userid);
		user.setAddress(address);
		
		UserDao userDao = new UserDao();
		userDao.updateDefaultAddress(user);
		
		session.setAttribute("address", address);
		String url = request.getContextPath() + "/ca1/checkout.jsp";
	    response.sendRedirect(url);
	}

}
