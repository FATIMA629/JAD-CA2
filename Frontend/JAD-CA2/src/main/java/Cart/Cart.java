package cart;

public class Cart {
	private int userid;
	private int bookid;
	private int quantity;
	
	public int getuserid() {
		return userid;
	}

	public void setuserid(int userid) {
		this.userid = userid;
	}

	public int getbookid() {
		return bookid;
	}

	public void setbookid(int bookid) {
		this.bookid = bookid;
	}

	public int getquantity() {
		return quantity;
	}

	public void setquantity(int quantity) {
		this.quantity = quantity;
	}

}