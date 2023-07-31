package AddressServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 * Servlet implementation class CreateNewAddress
 */
@WebServlet("/CreateNewAddress")
public class CreateNewAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewAddress() {
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
		//doGet(request, response);
		
		PrintWriter out = response.getWriter();
		Client client = ClientBuilder.newClient();
		String restUrl = "http://localhost:8081/store/address/createAddress";

		/*
		 * https://stackoverflow.com/questions/49600788/create-jax-rs-client-post-
		 * request-with-json-string-as-body
		 */
		JsonObject newAddress = Json.createObjectBuilder().add("address", request.getParameter("address"))
				.add("address2", request.getParameter("address2")).add("district", request.getParameter("district"))
				.add("cityId", request.getParameter("cityId")).add("postalCode", request.getParameter("postalCode"))
				.add("phone", request.getParameter("phone")).add("userId", request.getParameter("userId"))
				.build();

		Response resp = client.target(restUrl).request().post(Entity.json(newAddress));

		System.out.println("Status: " + resp.getStatus());

		if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
			System.out.println("Success");

			String rec = resp.readEntity(new GenericType<String>() {
			});

			request.setAttribute("rec", rec);
			String url = "/ca1/checkout.jsp";
			System.out.println(url);
			RequestDispatcher cd = request.getRequestDispatcher(url);
			cd.forward(request, response);
		} else {
			System.out.println("Failure");
			String url = "/ca1/checkout.jsp";
			request.setAttribute("err", "FailedInsertBook");
			RequestDispatcher cd = request.getRequestDispatcher(url);
			cd.forward(request, response);
		}
	}

}
