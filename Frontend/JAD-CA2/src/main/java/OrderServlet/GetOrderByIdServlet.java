package OrderServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import DBAccess.*;
import order.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Servlet implementation class GetOrderByIdServlet
 */
@WebServlet("/GetOrderByIdServlet")
public class GetOrderByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetOrderByIdServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		  int orderId = Integer.parseInt(request.getParameter("orderId"));
		// hardcode a testing value first
		int orderId = 1;
		System.out.println("Calling GetOrderByIdServlet...");

		Client client = ClientBuilder.newClient();
		String restUrl = "http://localhost:8081/store/orders/getOrderById/" + orderId;
		System.out.println("REST URL: " + restUrl);

		Response resp = client.target(restUrl).request(MediaType.APPLICATION_JSON).get();

		if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
			Order order = resp.readEntity(Order.class);
			System.out.println("Received Order: " + order.toString());
			System.out.println("Order Date: " + order.getOrderDate()); // For debugging purposes

			request.setAttribute("order", order);
			request.getRequestDispatcher("/show_order.jsp").forward(request, response);
		} else {
			System.out.println("Order not found!");
			request.setAttribute("message", "Order not found!");
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
