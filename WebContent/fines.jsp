<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />

<title>Fines</title>

<style>
body.padding2 {
	padding-top: 1cm;
	padding-left: 0.5cm;
}
</style>

<script type="text/javascript" src="js/jquery.js"></script>
<script src="js/jquery-ui.js"></script>

<script type="text/javascript">
	function ValidationSearch() {
		var borrower_id = $("#borrower_id").val();

		if (jQuery.trim(borrower_id).length == 0) {
			alert("Empty Search not allowed");
			return false;
		}

		return true;
	}

	function calculate_fines() {
		$.post(
				'calcFine',
				{
					"operation" : "calculate"
				},
				function(resp) {
					if (resp.type === "Fail") {
						$("#response_message").empty().append(
								'<div class="alert alert-dismissible alert-danger">'
										+ resp.message + '</div> <br><br>');
					} else if (resp.type === "Success") {
						$("#response_message").empty().append(
								'<div class="alert alert-dismissible alert-success">'
										+ resp.message + '</div>');
						
					}
				}).fail(function() {
			alert("Failed!");
		});
	}
	
	function pay_fines(borrower_id)
	{
		$.post(
				'calcFine',
				{
					"operation" : "pay_fine",
					"borrower_id" :  borrower_id
				},
				function(resp) {
					if (resp.type === "Fail") {
						$("#fine_response_message").empty().append(
								'<div class="alert alert-dismissible alert-danger">'
										+ resp.message + '</div> <br><br>');
					} else if (resp.type === "Success") {
						$("#fine_response_message").empty().append(
								'<div class="alert alert-dismissible alert-success">'
										+ resp.message + '</div>');
						
						$("#fine_tb").empty();
					}
				}).fail(function() {
			alert("Failed!");
		});
	}
</script>

<link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="padding2">
	<div class="col-lg-12">
		<div class="page-header">
			<h1 id="navbar">Fines</h1>
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
						<li><a href="#">Fines</a></li>
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

	<label for="calc" class="col-lg-2 control-label">Please click
		to get the latest Amounts</label>
	<button type="button" class="btn btn-success"
		onclick="calculate_fines()" id="calc" name="calc">
		Calculate Fines <span class="glyphicon glyphicon-play"></span>
	</button>
	<br>
	<br>

	<div class="col-lg-4">
		<div class="bs-component" id="response_message"></div>
	</div>

	<br />
	<br />
	<br />
	<br />

	<div class="row">
		<div class="col-lg-6">
			<div class="well bs-component">
				<form class="form-horizontal" action="calcFine" method="get"
					onsubmit="return ValidationSearch()">
					<fieldset>

						<legend>Fines Search</legend>

						<div class="form-group">
							<label for="borrower_id" class="col-lg-2 control-label">Borrower
								ID</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="borrower_id"
									name="borrower_id" placeholder="Borrower ID" pattern="\d{1,}"
									value="${searchQuery.borrowerId }">
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-10 col-lg-offset-2">
								<button type="reset" class="btn btn-default">Reset</button>
								<button type="submit" class="btn btn-primary">Search</button>
							</div>
						</div>
					</fieldset>
				</form>
				<div id="source-button" class="btn btn-primary btn-xs"
					style="display: none;">&lt; &gt;</div>
			</div>
		</div>
	</div>

	<div class="col-sm-12 col-md-10 col-md-offset-1">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>Book Details</th>
					<th>Due Date</th>
					<th>Late Days</th>
					<th class="text-center">Fine</th>
				</tr>
			</thead>
			<tbody id="fine_tb">
				<c:forEach items="${searchResult}" var="bookFine">
					<tr>
						<td class="col-sm-8 col-md-6">
							<div class="media">
								<div class="media-body">
									<h4 class="media-heading">ISBN: ${bookFine.bookId}</h4>
									<h5 class="media-heading">Branch ID: ${bookFine.branchId}</h5>
									<c:choose>
										<c:when test="${bookFine.status == 'Returned' }">
											<span>Status: </span>
											<span class="text-success"><strong>${bookFine.status }</strong></span>
										</c:when>
										<c:otherwise>
											<span>Status: </span>
											<span class="text-danger"><strong>${bookFine.status }</strong></span>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</td>
						<td class="col-md-1 text-center"><strong>${bookFine.dueDate}</strong></td>
						<td class="col-md-1 text-center"><strong>${bookFine.lateDays}</strong></td>
						<td class="col-md-1 text-center"><strong>${bookFine.fineAmt}
								$</strong></td>
					</tr>
				</c:forEach>
				<c:if test="${not empty searchResult}">
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>
							<h3>Total</h3>
						</td>
						<td class="text-right">
							<h3>
								<strong>${total } $</strong>
							</h3>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>
							<button type="button" class="btn btn-success" onclick="pay_fines('${searchQuery.borrowerId }')" id="pay_fine" name="pay_fine">Pay Fine <span class="glyphicon glyphicon-play"></span>
							</button>
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	
	<div class="col-lg-4">
		<div class="bs-component" id="fine_response_message"></div>
	</div>

</body>
</html>