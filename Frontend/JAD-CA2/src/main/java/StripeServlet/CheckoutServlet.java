package StripeServlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setAttribute("totalPrice", session.getAttribute("totalPrice"));
		System.out.println("Total price from checkoutservlet " + session.getAttribute("totalPrice"));
		request.setAttribute("stripePublicKey", "pk_test_51Na4b7JLhf6EaydIrHUGjamPvdvBjHGVw4p8d6cl3TRWAOVTKN9JHuvkEz3N4a1tJJsyFGg5QnWO0GXcXWAO2eUL00sAXrSuwr");
		
		String url = "ca1/payment.jsp";
		RequestDispatcher cd = request.getRequestDispatcher(url);
        cd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("stripeToken");
		String amountStr = request.getParameter("amount");
		String paymentType = request.getParameter("paymentType");
		double amountDouble = Double.parseDouble(amountStr);

		// Convert amount to smallest currency unit (cents for SGD)
		int amount = (int) (amountDouble * 100);
		String email = request.getParameter("stripeEmail");
		System.out.println(token);
		System.out.println(email);
		System.out.println(amount);
		
		Stripe.apiKey = "sk_test_51Na4b7JLhf6EaydIh5znAlX9SUNLGuXZk8fCspGlWbgyfq3EhZ4VjvEtOh6m7p3BIaMP7tC2u6hmqocMHT0bodI100oFcYjN0V";
		Map<String, Object> chargeParams = new HashMap<>();
		
        chargeParams.put("amount",amount);
        chargeParams.put("description", email);
        chargeParams.put("source", token);
        chargeParams.put("currency", "SGD");
        
        try {
			Charge.create(chargeParams);
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
				| APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        request.setAttribute("amount", amount);
        request.setAttribute("email", email);
        request.setAttribute("token", token);
        request.setAttribute("currency", "SGD");
        request.setAttribute("status", "success");
        request.setAttribute("paymentType", paymentType);
        
		String url = request.getContextPath() + "/OrderServlet";
		RequestDispatcher cd = request.getRequestDispatcher(url);
        cd.forward(request, response);
	}

}
