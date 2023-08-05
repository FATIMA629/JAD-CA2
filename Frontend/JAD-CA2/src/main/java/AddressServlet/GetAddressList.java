package AddressServlet;

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

import Address.Address;

/**
 * Servlet implementation class GetAddressList
 */
@WebServlet("/GetAddressList")
public class GetAddressList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAddressList() {
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
//        String userid = request.getParameter("userid");
        String restUrl = "http://localhost:8081/store/address/getAddressByUserId/1";
        WebTarget target = client.target(restUrl);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response resp = invocationBuilder.get();
        System.out.println("status: " + resp.getStatus());

        if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
            System.out.println("success");
            ArrayList<Address> addressList = resp.readEntity(new GenericType<ArrayList<Address>>() {});
            System.out.println(addressList.size());
            for (Address address : addressList) {
                System.out.println(address.getAddressId());
                out.print("<br>Address: " + address.getAddress());
                out.print("<br>CityId: " + address.getCityId());
                out.print("<br>UserId: " + address.getUserId() + "<br>");
            }

            request.setAttribute("addressList", addressList);
            System.out.println("......requestObj set...forwarding..");
            String url = "/ca1/checkout.jsp";
            System.out.println(url);
            RequestDispatcher cd = request.getRequestDispatcher(url);
            //cd.forward(request, response);
        } else {
            System.out.println("failed");
            String url = "/ca1/checkout.jsp";
            System.out.println(url);
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
