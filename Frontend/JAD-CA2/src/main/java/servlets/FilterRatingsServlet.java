package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rating.*;

/**
 * Servlet implementation class FilterRatingsServlet
 */
@WebServlet("/FilterRatingsServlet")
public class FilterRatingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilterRatingsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int bookId = Integer.parseInt(request.getParameter("id"));
		
		RatingDao ratingDao = null;
		try {
			ratingDao = new RatingDao();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Rating> ratingList = ratingDao.getRatingsForBook(bookId);
		
		session.setAttribute("ratingList", ratingList);
		session.setAttribute("bookId", bookId);
		response.sendRedirect(request.getContextPath() + "/ca1/viewBook.jsp");
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String selected = request.getParameter("selectedButton");
		System.out.println("Selected in servelt is " + selected);
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		System.out.println("bookId in servelt is " + bookId);
		List<Rating> ratingList = null;
		
		RatingDao ratingDao = null;
		try {
			ratingDao = new RatingDao();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(selected.equals("highest")) {
			 
			 ratingList= ratingDao.getHighestRating(bookId);
			
		} else if(selected.equals("lowest")) {
			 ratingList= ratingDao.getLowestRating(bookId);
			
		} else {
			ratingList= ratingDao.getRatingsForBook(bookId);
			
		}
		session.setAttribute("ratingList", ratingList);
		session.setAttribute("bookId", bookId);
		response.sendRedirect(request.getContextPath() + "/ca1/viewBook.jsp?selected=" + selected);
	}

}
