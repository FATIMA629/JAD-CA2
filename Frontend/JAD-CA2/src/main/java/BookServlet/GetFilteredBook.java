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
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import book.Book;

/**
 * Servlet implementation class GetFilteredBook
 */
@WebServlet("/GetFilteredBook")
public class GetFilteredBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFilteredBook() {
        super();
        // TODO Auto-generated constructor stub
    }	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
        Client client = ClientBuilder.newClient();
        String[] selectedGenres = request.getParameterValues("genre");
        double price = Double.parseDouble(request.getParameter("price"));
        
       
        if (selectedGenres != null) {
            for (String genreId : selectedGenres) {
                System.out.print(genreId + "<br>");
            }
        }
        
        System.out.print(price);
        String restUrl = "http://localhost:8081/store/books/getFilteredBooks?";
        
        if (selectedGenres != null && selectedGenres.length > 0) {
        	 restUrl += "genre=";
            	for (int i = 0; i < selectedGenres.length; i++) {
            	    if (i > 0) {
            	        restUrl += ",";
            	    }
            	    restUrl += selectedGenres[i];
            	}
        }
        
        restUrl += "&price=" +price;
        System.out.print(restUrl);
        
        WebTarget target = client.target(restUrl);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response resp = invocationBuilder.get();
        System.out.println("status: " + resp.getStatus());

        if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
            System.out.println("success");
            ArrayList<Book> filteredBooks = resp.readEntity(new GenericType<ArrayList<Book>>() {});
            System.out.println(filteredBooks.size());
            for (Book book : filteredBooks) {
                System.out.println(book.getBookId());
                out.print("<br>Bookid: " + book.getBookId());
                out.print("<br>Description: " + book.getDescription());
                out.print("<br>Author: " + book.getAuthor() + "<br>");
            }

            session.setAttribute("bookArray", filteredBooks);
            String url = "/ca1/filterResults.jsp";
            response.sendRedirect(url);
        } else {
            System.out.println("failed");
            session.setAttribute("err", "NotFound");
            String url = "/ca1/filterResults.jsp";
            response.sendRedirect(url);

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
