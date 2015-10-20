<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="Library">
<meta name="author" content="roshan">
<link rel="icon" href="../../favicon.ico">

<title>Create Borrower</title>

<style>
body.padding2 {
	padding-top: 1cm;
	padding-left: 0.5cm;
}
</style>

<script type="text/javascript" src="js/jquery.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="padding2">
	<div class="col-lg-12">
		<div class="page-header">
			<h1 id="navbar">Create Borrower</h1>
		</div>
		<div class="bs-component">
			<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="list_of_books.jsp">Home</a>
				</div>
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="#">Create Borrower</a></li>
						<li><a href="#">Check-in Book</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="cart.jsp">Go To Cart</a></li>
					</ul>
				</div>
			</div>
			</nav>
		</div>
	</div>

	<div class="row">
		<div class="col-lg-6">
			<div class="well bs-component">
				<form class="form-horizontal" action="addBorrower" method="post">
					<fieldset>
						<legend>Enter Borrower Details</legend>
						<div class="form-group">
							<label for="inputFname" class="col-lg-2 control-label">First
								Name</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="inputFname"
									name="inputFname" placeholder="First Name" required>
							</div>
						</div>

						<div class="form-group">
							<label for="inputLname" class="col-lg-2 control-label">Last
								Name</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="inputLname"
									name="inputLname" placeholder="Last Name" required>
							</div>
						</div>

						<div class="form-group">
							<label for="inputEmail" class="col-lg-2 control-label">Email</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="inputEmail"
									name="inputEmail" placeholder="abc@xyz.com"
									pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required>
							</div>
						</div>

						<div class="form-group">
							<label for="inputStreetAddress" class="col-lg-2 control-label">Street
								Address</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="inputStreetAddress"
									name="inputStreetAddress" placeholder="9999 Street Name"
									required>
							</div>
						</div>

						<div class="form-group">
							<label for="inputCity" class="col-lg-2 control-label">City</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="inputCity"
									name="inputCity" placeholder="City Name" required>
							</div>
						</div>

						<div class="form-group">
							<label for="inputState" class="col-lg-2 control-label">State</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="inputState"
									name="inputState" placeholder="State Name" required>
							</div>
						</div>

						<div class="form-group">
							<label for="inputPhone" class="col-lg-2 control-label">Phone
								Number</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="inputPhone"
									name="inputPhone" placeholder="(999) 999-9999"
									pattern="(\d{3}) ?\d{3}-?\d{4}" required>
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-10 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Cancel</button>
								<button type="submit" class="btn btn-primary">Submit</button>
							</div>
						</div>
					</fieldset>
				</form>
				<div id="source-button" class="btn btn-primary btn-xs"
					style="display: none;">&lt; &gt;</div>
			</div>
		</div>
	</div>
</body>
</html>