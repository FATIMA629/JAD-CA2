package servlets;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import book.*;
import javax.servlet.RequestDispatcher;



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

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
		HttpSession session = request.getSession();
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
        bookDao = new BookDao();
        
            // Retrieve the filtered results from the database
            List<Book> filteredBooks = bookDao.getFilteredBooks(selectedGenres, price);

            session.setAttribute("filteredBooks", filteredBooks);

            String url = request.getContextPath() + "/ca1/filterResults.jsp";
            response.sendRedirect(url);

	}
}