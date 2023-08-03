package CountryServlet;

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

import country.Country;

/**
 * Servlet implementation class GetCountry
 */
@WebServlet("/GetCountry")
public class GetCountry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCountry() {
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
        String restUrl = "http://localhost:8081/store/country/getCountry";
        WebTarget target = client.target(restUrl);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response resp = invocationBuilder.get();
        System.out.println("status: " + resp.getStatus());

        if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
            System.out.println("success");
            ArrayList<Country> countryList = resp.readEntity(new GenericType<ArrayList<Country>>() {});
            System.out.println(countryList.size());
            for (Country country : countryList) {
                System.out.println(country.getCountryId());
                out.print("<br>CountryId: " + country.getCountryId());
                out.print("<br>Country: " + country.getCountry());
            }

            request.setAttribute("countryArray", countryList);
            System.out.println("......requestObj set...forwarding..");
            String url = "/ca1/checkout.jsp";
            System.out.println(url);
            RequestDispatcher cd = request.getRequestDispatcher(url);
            cd.forward(request, response);
        } else {
            System.out.println("failed");
            String url = "/ca1/checkout.jsp";
            System.out.println(url);
            request.setAttribute("err", "NotFound");
            RequestDispatcher cd = request.getRequestDispatcher(url);
            cd.forward(request, response);
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
