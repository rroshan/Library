<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<title>Book Search</title>

<style>
body.padding2 {
	padding-top: 1cm;
	padding-left: 0.5cm;
}
</style>

<script type="text/javascript" src="js/jquery.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		populateCart();
	});

	function populateCart() {
		
		var card_no;
		if($("#card_no").length > 0) {
			card_no = $("#card_no").val();
		}
		else
		{
			card_no = '';	
		}
		

		$("#cart_table").empty().append(
				'<table class="table table-hover"></table>');

		var table = $('#cart_table').children();

		var key, val;
		var book;
		var row;

		row = '<thead><tr><th>Book</th><th>Branch</th><th>&nbsp;</th></tr></thead>';
		table.append(row);

		for (i = 0; i <= sessionStorage.length - 1; i++) {
			key = sessionStorage.key(i);
			val = sessionStorage.getItem(key);

			book = JSON.parse(val);

			row = '<tr></tr><td class="col-sm-8 col-md-6"><div class="media"> <a class="thumbnail pull-left"><img class="media-object" src="' + book.cover + '"/></a> <div class="media-body"><h4 class="media-heading">'
					+ book.title
					+ '</h4><h5 class="media-heading"> by '
					+ book.authors
					+ '</h5><h5 class="media-heading">ISBN: '
					+ book.isbn + '</h5></div></div></td>';
			row = row + '<td class="col-sm-1 col-md-1 text-center"><strong>'
					+ book.branchId + '</strong></td>';
			row = row
					+ '<td class="col-sm-1 col-md-1"> <button type="button" class="btn btn-danger" id="btn_'
					+ book.isbn
					+ '" onclick="deleteCartItem(this)"> <span class="glyphicon glyphicon-remove"></span> Remove </button></td></tr>';

			table.append(row);
		}

		row = '<tr><td> Borrower Card No: <input type="text" name="card_no" id="card_no" value="' + card_no + '"></td>   <td> <button type="button" class="btn btn-default" onclick="return morebooks_onclick()"> <span class="glyphicon glyphicon-shopping-cart"></span> Add More Books </button></td><td> <button type="button" class="btn btn-success" onclick="checkout()"> Checkout <span class="glyphicon glyphicon-play"></span> </button></td></tr>';
		table.append(row);
	}

	function deleteCartItem(button) {
		var button_id = button.id;
		var isbn = button_id.substr(4);
		sessionStorage.removeItem(isbn);

		populateCart();
	}

	function morebooks_onclick() {
		window.location.href = "http://localhost:8080/Library/list_of_books.jsp";
	}

	function displayMessage(messageObject) {

		if (messageObject.type === "Fail") {
			$("#response_message")
					.empty()
					.append(
							'<div class="alert alert-dismissible alert-danger">'
									+ messageObject.message + '</div>');
		} else if (messageObject.type === "Success") {
			$("#response_message")
					.empty()
					.append(
							'<div class="alert alert-dismissible alert-success">'
									+ messageObject.message + '</div>');
			sessionStorage.clear();
			populateCart();
		}
	}

	function checkout() {
		var borrower_id = $("#card_no").val();
		
		if(sessionStorage.length < 1) {
			alert("Cart is Empty. Cannot checkout");
			return false;
		}

		if (borrower_id.length != 6) {
			alert("Please enter a valid 6 digit card number");
			return false;
		}

		//get all session storage json
		var cartArr = [];
		for (i = 0; i <= sessionStorage.length - 1; i++) {
			key = sessionStorage.key(i);
			val = sessionStorage.getItem(key);

			cartArr[i] = val;
		}

		$.post('checkoutBooks', {
			"data" : JSON.stringify(cartArr),
			"borrowerId" : borrower_id
		}, function(resp) {
			displayMessage(resp);
		}).fail(function() {
			alert("Failed!");
		});
	}
</script>
<link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div class="col-lg-12">
		<div class="page-header">
			<h1 id="navbar">Cart</h1>
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
						<li><a href="create_borrower.jsp">Create Borrower</a></li>
						<li><a href="book_checkin.jsp">Check-in Book</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="cart.jsp">Go To Cart</a></li>
					</ul>
				</div>
			</div>
			</nav>
		</div>
		<!-- /example -->
	</div>

	<div class="col-lg-12"></div>
	<div class="container">
		<div class="row">
			<div class="col-sm-12 col-md-10 col-md-offset-1" id="cart_table">

			</div>
		</div>
	</div>

	<div class="col-lg-4">
		<div class="bs-component" id="response_message"></div>
	</div>
</body>
</html>