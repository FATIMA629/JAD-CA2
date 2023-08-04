package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Address.*;



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
		String addressId = request.getParameter("selectedAddress");
		System.out.println(addressId);

		response.sendRedirect(request.getContextPath() + "/CheckoutServlet");	
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String Address = request.getParameter("address");
		String address2 = request.getParameter("address2");
		String district = request.getParameter("district");
		int cityId = Integer.parseInt(request.getParameter("cityId"));
		int postalCode = Integer.parseInt(request.getParameter("postalCode"));
		int phone = Integer.parseInt(request.getParameter("phone"));
        int userid = (int) session.getAttribute("userId");
        

        Address address = new Address();
        address.setAddress(Address);
        address.setAddress2(address2);
        address.setDistrict(district);
        address.setCityId(cityId);
        address.setPostalCode(postalCode);
        address.setPhone(phone);
        address.setUserId(userid);
		
        AddressDao addressDao = new AddressDao();
        try {
			addressDao.createAddress(address);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        response.sendRedirect(request.getContextPath() + "/ca1/checkout.jsp");
	}

}
