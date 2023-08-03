package StripeServlet;


public class ChargeRequest {
	    private String description;
	    private int amount;
	    private String stripeEmail;
	    public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public int getAmount() {
			return amount;
		}
		public void setAmount(int amount) {
			this.amount = amount;
		}
		public String getStripeEmail() {
			return stripeEmail;
		}
		public void setStripeEmail(String stripeEmail) {
			this.stripeEmail = stripeEmail;
		}
		public String getStripeToken() {
			return stripeToken;
		}
		public void setStripeToken(String stripeToken) {
			this.stripeToken = stripeToken;
		}
		private String stripeToken;
}
