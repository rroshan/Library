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

<title>Check-in Book</title>

<style>
body.padding2 {
	padding-top: 1cm;
	padding-left: 0.5cm;
}
</style>

<script type="text/javascript" src="js/jquery.js"></script>
<script src="js/jquery-ui.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$(function() {
			$("#isbn").autocomplete({
				source : function(request, response) {
					$.ajax({
						url : "autoComplete",
						type : "GET",
						data : {
							term : request.term,
							type : "isbn"
						},
						dataType : "json",
						success : function(data) {
							response(data);
						}
					});
				}
			});
		});

		$(function() {
			$("#name").autocomplete({
				source : function(request, response) {
					$.ajax({
						url : "autoComplete",
						type : "GET",
						data : {
							term : request.term,
							type : "name"
						},
						dataType : "json",
						success : function(data) {
							response(data);
						}
					});
				}
			});
		});
	});

	function ValidationSearch() {
		var isbn = $("#isbn").val();
		var borrower_id = $("#borrower_id").val();
		var name = $("#name").val();
		if (jQuery.trim(isbn).length == 0
				&& jQuery.trim(borrower_id).length == 0
				&& jQuery.trim(name).length == 0) {
			alert("Empty Search not allowed");
			return false;
		}

		return true;
	}

	function checkInBook(button) {
		var loanId = $(button).closest('tr').children('td.loanId').text();
		var isbn = $("#isbn").val();
		var borrower_id = $("#borrower_id").val();
		var name = $("#name").val();

		$.post('checkinBook', {
			"loanId" : loanId,
			"isbn" : isbn,
			"borrower_id" : borrower_id,
			"name" : name,
			"operation" : "checkin"
		}, function(resp) {
			location.reload(true);
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
			<h1 id="navbar">Check-in Book</h1>
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
						<li><a href="#">Check-in Book</a></li>
						<li><a href="fines.jsp">Fines</a></li>
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
				<form class="form-horizontal" action="checkinBook" method="get"
					onsubmit="return ValidationSearch()">
					<fieldset>

						<legend>Check-in Search</legend>

						<div class="form-group">
							<label for="isbn" class="col-lg-2 control-label">ISBN</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="isbn" name="isbn"
									placeholder="ISBN 10-Digit" pattern="[A-Za-z0-9]{10}"
									value="${searchQuery.isbn }">
							</div>
						</div>

						<div class="form-group">
							<label for="borrower_id" class="col-lg-2 control-label">Card
								Number</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="borrower_id"
									name="borrower_id" placeholder="Borrower ID"
									value="${searchQuery.cardNo }" pattern="\d{1,}">
							</div>
						</div>

						<div class="form-group">
							<label for="name" class="col-lg-2 control-label">Borrower
								Name</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="name" name="name"
									placeholder="Borrower Name" value="${searchQuery.name }">
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

	<div class="col-lg-12">
		<div class="page-header">
			<h1 id="tasks">Search Result</h1>
		</div>

		<div class="bs-component">
			<table class="table table-striped table-hover " id="resultTable">
				<tr>
					<th>Loan ID</th>
					<th>ISBN</th>
					<th>Branch ID</th>
					<th>Borrower ID</th>
					<th>Borrower Name</th>
					<th>Date Out</th>
					<th>Date Due</th>
				</tr>
				<c:forEach items="${searchResult}" var="currentLoan">
					<tr class="active" id="${currentLoan.loanId}">
						<td class="loanId"><c:out value="${currentLoan.loanId}" /></td>
						<td class="isbn"><c:out value="${currentLoan.isbn}" /></td>
						<td class="branchId"><c:out value="${currentLoan.branchId}" /></td>
						<td class="cardNo"><c:out value="${currentLoan.cardNo}" /></td>
						<td class="name"><c:out value="${currentLoan.name}" /></td>
						<td class="dateOut"><c:out value="${currentLoan.dateOut}" /></td>
						<td class="dateDue"><c:out value="${currentLoan.dateDue}" /></td>
						<td><button onclick="checkInBook(this)"
								id="button_${currentLoan.loanId}" class='btn btn-success'>Check-In</button>
					</tr>
				</c:forEach>
			</table>
			<div id="source-button" class="btn btn-primary btn-xs"
				style="display: none;">&lt; &gt;</div>
		</div>
	</div>
</body>
</html>