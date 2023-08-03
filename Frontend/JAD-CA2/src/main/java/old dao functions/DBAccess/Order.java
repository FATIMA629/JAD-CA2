package DBAccess;

import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;

public class Order {
	private int orderId;
	private int userId;
	private double totalPrice;
	private Date orderDate;
	private String orderStatus;
	private String shippingAddress;
	private String billingAddress;
	private String postalCode;
	private String country;
    private List<OrderItem> orderItems;

    // Getter and setter for orderItems
    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
	// Constructors, getters, and setters

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(java.sql.Date date) {
		// try {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// this.orderDate = dateFormat.parse(date);
		this.orderDate = date;
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
