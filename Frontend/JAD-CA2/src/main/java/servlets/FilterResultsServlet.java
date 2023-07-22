package servlets;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import Books.BookDao;
import Books.Book;
import javax.servlet.RequestDispatcher;



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FilterResultsServlet
 */
@WebServlet("/FilterResultsServlet")
public class FilterResultsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilterResultsServlet() {
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
		// Retrieve the selected filter values from the request parameters
        String[] selectedGenres = request.getParameterValues("genre");
        if (selectedGenres != null) {
            for (String genreId : selectedGenres) {
                System.out.print(genreId + "<br>");
            }
        }
        double price = Double.parseDouble(request.getParameter("price"));
        System.out.print(price);
        
     // Create an instance of the BookDao class
        BookDao bookDao = null;
        try {
            bookDao = new BookDao();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Check if the bookDao object was successfully initialized
        if (bookDao != null) {
            // Retrieve the filtered results from the database
            List<Book> filteredBooks = bookDao.getFilteredBooks(selectedGenres, price);

            // Set the filtered results as a request attribute
            request.setAttribute("filteredBooks", filteredBooks);

            // Forward the request to a JSP page to display the results
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ca1/filterResults.jsp");
            dispatcher.forward(request, response);
        } else {
           
        }

	}
}