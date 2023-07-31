package BookServlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

@WebServlet("/CreateNewBook")
public class CreateNewBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateNewBook() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Client client = ClientBuilder.newClient();
		String restUrl = "http://localhost:8081/store/books/createBook";

        // get the image file
        Part imagePart = request.getPart("image");
        InputStream imageInputStream = imagePart.getInputStream();

        // Use the BufferedImage class to read the image data from the InputStream object
        String imageName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        String imageFormat = imageName.substring(imageName.lastIndexOf(".") + 1);
        BufferedImage image = ImageIO.read(imageInputStream);

        // Specify the absolute path to your Eclipse project's images folder
        String imagesDir = "C:\\Users\\DC\\eclipse-workspace\\JAD-Assignement-2\\Frontend\\JAD-CA2\\src\\main\\webapp\\images";

        // Use the ImageIO class to write the BufferedImage object to a file in your images folder
        File outputFile = new File(imagesDir + File.separator + imageName);

        // Check if the file already exists
        if (outputFile.exists()) {
            System.out.println("The image already exists at: " + outputFile.getAbsolutePath());
        } else {
            // Use the ImageIO class to write the BufferedImage object to a file in your images folder
            ImageIO.write(image, imageFormat, outputFile);
            System.out.println("The image was saved to: " + outputFile.getAbsolutePath());
        }

        // Get the relative path of the saved image file
        String imagePath = "images" + File.separator + imageName;
        System.out.println("The image was saved to: " + imagePath);

		JsonObject newBook = Json.createObjectBuilder()
				.add("title", request.getParameter("title"))
				.add("author", request.getParameter("author"))
				.add("genreId", Integer.parseInt(request.getParameter("genreId")))
				.add("price", Double.parseDouble(request.getParameter("price")))
				.add("quantity", Integer.parseInt(request.getParameter("quantity")))
				.add("publisher", request.getParameter("publisher"))
				.add("publishDate", request.getParameter("publishDate"))
				.add("isbn", request.getParameter("isbn"))
				.add("rating", Double.parseDouble(request.getParameter("rating")))
				.add("description", request.getParameter("description"))
				.add("imageLocation", imagePath)
				.add("sold", Integer.parseInt(request.getParameter("sold")))
				.build();

		Response resp = client.target(restUrl).request().post(Entity.json(newBook));

		if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
			System.out.println("Success");
			String rec = resp.readEntity(new GenericType<String>() {});
			request.setAttribute("rec", rec);
			String url = "/ca1/adminDashboard.jsp";
			RequestDispatcher cd = request.getRequestDispatcher(url);
			cd.forward(request, response);
		} else {
			System.out.println("Failure");
			String url = "/ca1/adminDashboard.jsp";
			request.setAttribute("err", "FailedInsertBook");
			RequestDispatcher cd = request.getRequestDispatcher(url);
			cd.forward(request, response);
		}
	}
}
