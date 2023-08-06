package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rating.*;

/**
 * Servlet implementation class AddRatingServlet
 */
@WebServlet("/AddRatingServlet")
public class AddRatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRatingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		
		request.setAttribute("bookId", bookId);
		String url = "/ca1/addRating.jsp";
		RequestDispatcher cd = request.getRequestDispatcher(url);
        cd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("errors");
		session.removeAttribute("inputData");
		session.removeAttribute("success");
		int userId = (int) session.getAttribute("userId");
		
		System.out.println("Entered doPost method in AddRatingServlet");
		System.out.println(request.getParameter("bookId"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String Rating = request.getParameter("rating");
		String comment = request.getParameter("comment");
		
		Map<String, String> errors = new HashMap<>();
		Pattern decimalPattern = Pattern.compile("\\d+(\\.\\d+)?");
		
		if (!decimalPattern.matcher(Rating).matches()) {
			errors.put("rating", "Rating should be a number");
		}
		if (comment == null || comment.isEmpty()) {
			errors.put("comment", "Description is required");
		}
		
		if (errors.isEmpty()) {
            System.out.println("No validation errors found. Add Rating.");

			Rating rating = new Rating();
			rating.setBookId(bookId);
			rating.setUserId(userId);
			rating.setRating(Double.parseDouble(Rating));
			rating.setHelpful(0);
			rating.setComment(comment);

			RatingDao ratingDao = null;
			try {
				ratingDao = new RatingDao();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ratingDao.addRating(rating);

			session.setAttribute("success", "Rate added successfully!");
			response.sendRedirect(request.getContextPath() + "/ca1/purchaseHistory.jsp");
		} else {
            System.out.println("Validation errors found. Not add rating.");

			Map<String, String> inputData = new HashMap<>();
			inputData.put("rating", Rating);
			inputData.put("comment", comment);
			

			request.getSession().setAttribute("inputData", inputData);
			request.getSession().setAttribute("errors", errors);

			response.sendRedirect(request.getContextPath() + "/ca1/addRating.jsp");
		}
	}

}
