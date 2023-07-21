package CartServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Books.Book;

/**
 * Servlet implementation class AddToCart
 */
@WebServlet("/AddToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCart() {
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
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Client client = ClientBuilder.newClient();
		HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        int bookId = Integer.parseInt(request.getParameter("id"));
        int cartQuantity = Integer.parseInt(request.getParameter("cart-quantity"));
		String restUrl = "http://localhost:8081/store/books/createBook/" + userId + "/" + bookId + "/" + cartQuantity;
		
		/*
		 * https://stackoverflow.com/questions/49600788/create-jax-rs-client-post-
		 * request-with-json-string-as-body
		 */
		JsonObject newCartItem = Json.createObjectBuilder().add("userid", userId).add("bookid", bookId)
				.add("quantity", cartQuantity).build();

		Response resp = client.target(restUrl).request().post(Entity.json(newCartItem));
		
		System.out.println("Status: " + resp.getStatus());

		if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
			System.out.println("Success");

			String rec = resp.readEntity(new GenericType<String>() {
			});

			request.setAttribute("rec", rec);
			String url = request.getContextPath() + "/ca1/cart.jsp";
			System.out.println(url);
			RequestDispatcher cd = request.getRequestDispatcher(url);
			cd.forward(request, response);
		} else {
			System.out.println("Failure");
			String url = "ca1/cart.jsp";
			request.setAttribute("err", "FailedInsertBook");
			RequestDispatcher cd = request.getRequestDispatcher(url);
			cd.forward(request, response);
		}
	}

}
