package BookServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import book.Book;

/**
 * Servlet implementation class GetAverageRatingOfAllBook
 */
@WebServlet("/GetAverageRatingOfAllBook")
public class GetAverageRatingOfAllBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAverageRatingOfAllBook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		PrintWriter out = response.getWriter();
        Client client = ClientBuilder.newClient();
        String restUrl = "http://localhost:8081/store/books/getAverageRatingOfAllBooks";
        WebTarget target = client.target(restUrl);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response resp = invocationBuilder.get();
        System.out.println("status: " + resp.getStatus());

        if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
            System.out.println("success");

			Double rec = resp.readEntity(new GenericType<Double>() {
			});

			request.setAttribute("rec", rec);
			out.print(rec);
            System.out.println("......requestObj set...forwarding..");
            String url = "ca1/home.jsp";
            RequestDispatcher cd = request.getRequestDispatcher(url);
            //cd.forward(request, response);
        } else {
            System.out.println("failed");
            String url = "ca1/home.jsp";
            request.setAttribute("err", "NotFound");
            RequestDispatcher cd = request.getRequestDispatcher(url);
            //cd.forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
