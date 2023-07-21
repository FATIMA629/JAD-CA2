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

import Books.Book;

/**
 * Servlet implementation class GetHighestRatedBook
 */
@WebServlet("/GetHighestRatedBook")
public class GetHighestRatedBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetHighestRatedBook() {
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
        String restUrl = "http://localhost:8081/store/books/getHighestRatedBooks/3";
        WebTarget target = client.target(restUrl);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response resp = invocationBuilder.get();
        System.out.println("status: " + resp.getStatus());

        if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
            System.out.println("success");
            ArrayList<Book> bookList = resp.readEntity(new GenericType<ArrayList<Book>>() {});
            System.out.println(bookList.size());
            for (Book book : bookList) {
                System.out.println(book.getBookId());
                out.print("<br>Title: " + book.getTitle());
                out.print("<br>Description: " + book.getDescription());
                out.print("<br>Author: " + book.getAuthor() + "<br>");
            }

            request.setAttribute("bookArray", bookList);
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
