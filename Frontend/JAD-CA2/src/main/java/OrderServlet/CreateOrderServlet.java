package OrderServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DBAccess.*;

/**
 * Servlet implementation class CreateOrderServlet
 */
@WebServlet("/CreateOrderServlet")
public class CreateOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateOrderServlet() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Parse the JSON request body and map it to an Order object
            Order order = new Order();
            order.setUserId(Integer.parseInt(request.getParameter("userId")));
            order.setTotalPrice(Double.parseDouble(request.getParameter("totalPrice")));
            // Set other order properties accordingly based on request parameters

            // Call the DAO to create the order
            OrderDao orderDao = new OrderDao();
            boolean created = orderDao.createOrder(order);

            if (created) {
                // Order created successfully
                response.getWriter().print("{\"success\": true}");
            } else {
                // Order creation failed
                response.getWriter().print("{\"success\": false}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

}
