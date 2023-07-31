<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Checkout Form</title>
	<link rel="stylesheet" href="css/checkout.css">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
</head>
<body>


		<div class="col-md-4 container bg-default">
			
			<h4 class="my-4">
					Billing Address
			</h4>
			
			<form>
				<div class="form-group">
					<label for="adress">Address</label>
					<input type="text" class="form-control" id="adress" placeholder="1234 Main Street" required>
					<div class="invalid-feedback">
						Please enter your shipping address.
					</div>
				</div>

				<div class="form-group">
					<label for="address2">Address 2
						<span class="text-muted">(Optional)</span>
					</label>
					<input type="text" class="form-control" id="adress2" placeholder="Flat No">
				</div>

				<div class="row">
				<div class="col-md-4 form-group">
						<label for="district">District</label>
					<input type="text" class="form-control" id="district" placeholder="Singapore" name="district">
						<div class="invalid-feedback">
							District required.
						</div>
					</div>
			
			
		
					<div class="col-md-4 form-group">
						<label for="country">Country</label>
						<select type="text" class="form-control" id="country">
							<option value>Choose...</option>
							<option>United Kingdom</option>
						</select>
						<div class="invalid-feedback">
							Please select a valid country.
						</div>	
					</div>
	


					<div class="col-md-4 form-group">
						<label for="city">City</label>
						<select type="text" class="form-control" id="city">
							<option value>Choose...</option>
							<option>London</option>
						</select>
						<div class="invalid-feedback">
							Please provide a valid city.
						</div>
					</div>
					</div>
					
					<div class="row">
					<div class="col-md-6 form-group">
						<label for="postalCode">Postal Code</label>
						<input type="text" class="form-control" id="postalCode" placeholder="092342" name="postalCode">
						<div class="invalid-feedback">
							Valid postal code is required.
						</div>
					</div>

					<div class="col-md-6 form-group">
						<label for="phone">Phone Number</label>
						<input type="text" class="form-control" id="phone" placeholder="09009121" name="phone">
						<div class="invalid-feedback">
							Valid phone number is required.
						</div>
					</div>
				</div>

				<hr>
				
				<div class="form-check">
					<input type="checkbox" class="form-check-input" id="shipping-adress"> 
						Shipping address is the same as my billing address
					<label for="shipping-adress" class="form-check-label"></label>
				</div>

				<div class="form-check">
					<input type="checkbox" class="form-check-input" id="same-adress">
						Save this information for next time
					<label for="same-adress" class="form-check-label"></label>					
					</div>

				<hr>

				<h4 class="mb-4">Payment</h4>
				
				<div class="form-check">
					<input type="radio" class="form-check-input" id="credit" name="payment-method" checked required>
					<label for="credit" class="form-check-label">Credit Card</label>
				</div>

				<div class="form-check">
					<input type="radio" class="form-check-input" id="debit" name="payment-method" required>
					<label for="debit" class="form-check-label">Debit Card</label>
				</div>

				<div class="form-check">
					<input type="radio" class="form-check-input" id="paypal" name="payment-method"  required>
					<label for="paypal" class="form-check-label">PayPal</label>
				</div>
			
				<div class="row mt-4">
					<div class="col-md-6 form-group">
							<label for="card-name">Name on card</label>
							<input type="text" class="form-control" id="card-name" placeholder required>
							<div class="invalid-feedback">
								Name on card is required
							</div>
						</div>

						<div class="col-md-6 form-group">
							<label for="card-no">Card Number</label>
							<input type="text" class="form-control" id="card-no" placeholder required>
							<div class="invalid-feedback">
								Credit card number is required
							</div>
						</div>
				</div>

				<div class="form-row">
					<div class="col-md-5 form-group">
							<label for="expiration">Expire Date</label>
							<input type="text" class="form-control" id="card-name" placeholder required>
							<div class="invalid-feedback">
								Expiration date required
							</div>
						</div>
					

					<div class="col-md-5 form-group">
							<label for="ccv-no">Security Number</label>
							<input type="text" class="form-control" id="sec-no" placeholder required>
							<div class="invalid-feedback">
								Security code required
							</div>
					</div>
				</div>

					<hr class="mb-4">
				
					<button class="btn btn-primary bt-lg btn-block" type="submit">Continue to Checkout</button>
			</form>
		</div>
</body>
</html>