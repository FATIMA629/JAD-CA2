package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import book.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.lang.ClassNotFoundException;
import javax.servlet.annotation.MultipartConfig;

@WebServlet("/AddBookServlet")
@MultipartConfig
public class AddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDao bookDao;

	public AddBookServlet() {
		super();
		bookDao = new BookDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("errors");
		request.getSession().removeAttribute("inputData");
		request.getSession().removeAttribute("success");

		// get the image file
		Part imagePart = request.getPart("imageLocation");
		InputStream imageInputStream = imagePart.getInputStream();
		String imageName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		String imageFormat = imageName.substring(imageName.lastIndexOf(".") + 1);
		BufferedImage image = ImageIO.read(imageInputStream);

		// Specify the absolute path to your project's images folder
		String imagesDir = "C:\\Users\\DC\\eclipse-workspace\\JAD-Assignement-2\\Frontend\\JAD-CA2\\src\\main\\webapp\\images";

		// Use the ImageIO class to write the BufferedImage object to a file in your
		// images folder
		File outputFile = new File(imagesDir + File.separator + imageName);
		ImageIO.write(image, imageFormat, outputFile);

		// Get the relative path of the saved image file
		String imageLocation = "images" + File.separator + imageName;

		System.out.println("Image Part received");
		System.out.println("Image Name: " + imageName);
		System.out.println("Image Format: " + imageFormat);

		System.out.println("Entered doPost method in AddBookServlet");
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String genreId = request.getParameter("genreId");
		String price = request.getParameter("price");
		String quantity = request.getParameter("quantity");
		String publisher = request.getParameter("publisher");
		String publishDate = request.getParameter("publishDate");
		String isbn = request.getParameter("isbn");
		String rating = request.getParameter("rating");
		String description = request.getParameter("description");
		String sold = request.getParameter("sold");

		System.out.println("Title: " + title);
		System.out.println("Author: " + author);
		System.out.println("GenreId: " + genreId);
		System.out.println("Price: " + price);
		System.out.println("Quantity: " + quantity);
		System.out.println("Publisher: " + publisher);
		System.out.println("PublishDate: " + publishDate);
		System.out.println("ISBN: " + isbn);
		System.out.println("Rating: " + rating);
		System.out.println("Description: " + description);
		System.out.println("Sold: " + sold);

		Map<String, String> errors = new HashMap<>();
		Pattern decimalPattern = Pattern.compile("\\d+(\\.\\d+)?");
		Pattern integerPattern = Pattern.compile("\\d+");

		if (title == null || title.isEmpty()) {
			System.out.println("Validation failed: Title is required");
			errors.put("title", "Title is required");
		}
		if (author == null || author.isEmpty()) {
			errors.put("author", "Author is required");
		}
		if (genreId == null || genreId.trim().isEmpty()) {
			errors.put("genreId", "Genre must be selected");
		}
		if (!decimalPattern.matcher(price).matches()) {
			errors.put("price", "Price should be a number");
		}
		if (!integerPattern.matcher(quantity).matches()) {
			errors.put("quantity", "Quantity should be a whole number");
		}
		if (publisher == null || publisher.isEmpty()) {
			errors.put("publisher", "Publisher is required");
		}
		if (isbn == null || isbn.isEmpty()) {
			errors.put("isbn", "ISBN is required");
		}
		if (!decimalPattern.matcher(rating).matches()) {
			errors.put("rating", "Rating should be a number");
		}
		if (description == null || description.isEmpty()) {
			errors.put("description", "Description is required");
		}
		if (imageLocation == null || imageLocation.isEmpty()) {
			errors.put("imageLocation", "Image Location is required");
		}
		if (!integerPattern.matcher(sold).matches()) {
			errors.put("sold", "Sold should be a whole number");
		}

		if (bookDao.getBookByIsbn(isbn) != null) {
			errors.put("isbn", "A book with this ISBN already exists");
		}

		LocalDate now = LocalDate.now();
		LocalDate publishLocalDate = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			publishLocalDate = LocalDate.parse(publishDate, formatter);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			errors.put("publishDate", "Invalid date format");
		}

        System.out.println("Errors: " + errors);

        		if (publishLocalDate != null && (publishLocalDate.isEqual(now) || publishLocalDate.isAfter(now))) {
			errors.put("publishDate", "Publish date should be in the past");
            System.out.println("Publish date is in the future");

		}

		if (errors.isEmpty()) {
            System.out.println("No validation errors found. Creating book.");

			Book book = new Book();
			book.setTitle(title);
			book.setAuthor(author);
			book.setGenreId(Integer.parseInt(genreId));
			book.setPrice(Double.parseDouble(price));
			book.setQuantity(Integer.parseInt(quantity));
			book.setPublisher(publisher);
			book.setPublishDate(publishDate);
			book.setIsbn(isbn);
			book.setRating(Double.parseDouble(rating));
			book.setDescription(description);
			book.setImageLocation(imageLocation);
			book.setSold(Integer.parseInt(sold));

			try {
				bookDao.createBook(book);
			} catch (ClassNotFoundException | SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.getSession().setAttribute("success", "Book created successfully!");
		} else {
            System.out.println("Validation errors found. Not creating book.");

			Map<String, String> inputData = new HashMap<>();
			inputData.put("title", title);
			inputData.put("author", author);
			inputData.put("genreId", genreId);
			inputData.put("price", price);
			inputData.put("quantity", quantity);
			inputData.put("publisher", publisher);
			inputData.put("publishDate", publishDate);
			inputData.put("isbn", isbn);
			inputData.put("rating", rating);
			inputData.put("description", description);
			inputData.put("imageLocation", imageLocation);
			inputData.put("sold", sold);

			request.getSession().setAttribute("inputData", inputData);
			request.getSession().setAttribute("errors", errors);

		}
        System.out.println("Redirecting to admin dashboard");

		response.sendRedirect(request.getContextPath() + "/ca1/adminDashboard.jsp");
	}
}
