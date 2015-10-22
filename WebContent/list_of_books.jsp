<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.library.bean.*"%>
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

<title>Book Search</title>

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
			$("#title").autocomplete({
				source : function(request, response) {
					$.ajax({
						url : "autoComplete",
						type : "GET",
						data : {
							term : request.term,
							type : "title"
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
			$("#author").autocomplete({
				source : function(request, response) {
					$.ajax({
						url : "autoComplete",
						type : "GET",
						data : {
							term : request.term,
							type : "author"
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

	function addToCart(button) {
		var title = $(button).closest('tr').children('td.title').text();
		var isbn = $(button).closest('tr').children('td.isbn').text();
		var authors = $(button).closest('tr').children('td.authors').text();
		var branchId = $(button).closest('tr').children('td.branchId').text();
		var img_td = $(button).closest('tr').children('td.cover');
		var img = $(img_td).children('img').attr("src");

		var book = new Object();
		book.isbn = isbn;
		book.title = title;
		book.authors = authors;
		book.branchId = branchId;
		book.cover = img;

		sessionStorage.setItem(isbn, JSON.stringify(book));

		alert("Successfully Added to Cart");
	}

	function ValidationSearch() {
		var isbn = $("#isbn").val();
		var title = $("#title").val();
		var author = $("#author").val();
		if (jQuery.trim(isbn).length == 0 && jQuery.trim(title).length == 0
				&& jQuery.trim(author).length == 0)
			alert("Empty Search not allowed");
	}
</script>
<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

</head>
<body class="padding2">
	<%
		ArrayList<SearchBookResultBean> searchResult = (ArrayList<SearchBookResultBean>) request.getAttribute("searchResult");
		SearchBookBean searchQuery = (SearchBookBean) request.getAttribute("searchQuery");
		
		pageContext.setAttribute("searchResult", searchResult);
		pageContext.setAttribute("searchQuery", searchQuery);
	%>
	<div class="col-lg-12">
		<div class="page-header">
			<h1 id="navbar">Library</h1>
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
					<a class="navbar-brand" href="#">Home</a>
				</div>
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="create_borrower.jsp">Create Borrower</a></li>
						<li><a href="#">Check-in Book</a></li>
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

	<div class="row">
		<div class="col-lg-6">
			<div class="well bs-component">
				<form class="form-horizontal" action="searchBook" method="get"
					onsubmit="return ValidationSearch()">
					<fieldset>

						<legend>Search for a book</legend>

						<div class="form-group">
							<label for="isbn" class="col-lg-2 control-label">ISBN</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="isbn" name="isbn"
									placeholder="ISBN 10-Digit" pattern="\d{10}"
									value="${searchQuery.isbn }">
							</div>
						</div>

						<div class="form-group">
							<label for="title" class="col-lg-2 control-label">Book
								Title</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="title" name="title"
									placeholder="Book Title" value="${searchQuery.title }">
							</div>
						</div>

						<div class="form-group">
							<label for="author" class="col-lg-2 control-label">Book
								Author</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" id="author"
									name="author" placeholder="Book Author"
									value="${searchQuery.author }">
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
					<th>Cover</th>
					<th>ISBN</th>
					<th>Book Title</th>
					<th>Author(s)</th>
					<th>Branch ID</th>
					<th>Total Copies in Branch</th>
					<th>Copies available in Branch</th>
				</tr>
				<c:forEach items="${searchResult}" var="currentBook">
					<tr class="active" id="${currentBook.book.isbn}">
						<td class="cover"><img src="${currentBook.book.cover}"
							alt="${currentBook.book.title}" /></td>
						<td class="isbn"><c:out value="${currentBook.book.isbn}" /></td>
						<td class="title"><c:out value="${currentBook.book.title}" /></td>
						<td class="authors"><c:out
								value="${currentBook.book.authors}" /></td>
						<td class="branchId"><c:out value="${currentBook.branchId}" /></td>
						<td><c:out value="${currentBook.noOfCopiesBranch}" /></td>
						<td><c:out value="${currentBook.availableCopiesBranch}" /></td>
						<td><button onclick="addToCart(this)"
								id="button_${currentBook.book.isbn}" class='btn btn-success'>Add
								to Cart</button>
					</tr>
				</c:forEach>
			</table>
			<div id="source-button" class="btn btn-primary btn-xs"
				style="display: none;">&lt; &gt;</div>
		</div>

		<div class="bs-component" align="center">
			<ul class="pagination pagination-lg">
				<c:if test="${currentPage > 1}">
					<li><a
						href="searchBook?page=1&isbn=${searchQuery.isbn }&title=${searchQuery.title }&author=${searchQuery.author }">First</a></li>
					<li><a
						href="searchBook?page=${currentPage - 1}&isbn=${searchQuery.isbn }&title=${searchQuery.title }&author=${searchQuery.author }">«</a></li>
				</c:if>

				<c:choose>
					<c:when test="${(currentPage - 5) lt 0}">
						<c:set var="begin" value="${1}" />

						<c:choose>
							<c:when test="${noOfPages lt 10 }">
								<c:set var="end" value="${noOfPages}" />
							</c:when>
							<c:otherwise>
								<c:set var="end" value="${10}" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<c:set var="begin" value="${1 + currentPage - 5}" />
						<c:choose>
							<c:when test="${noOfPages lt (currentPage + 5)}">
								<c:set var="end" value="${noOfPages}" />
							</c:when>
							<c:otherwise>
								<c:set var="end" value="${currentPage + 5}" />
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				
				<c:forEach begin="${begin}" end="${end}" var="i">
					<c:choose>
						<c:when test="${currentPage eq i}">
							<li class="active"><a href="#">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li><a
								href="searchBook?page=${i}&isbn=${searchQuery.isbn }&title=${searchQuery.title }&author=${searchQuery.author }">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<c:if test="${currentPage lt noOfPages}">
					<li><a
						href="searchBook?page=${currentPage + 1}&isbn=${searchQuery.isbn }&title=${searchQuery.title }&author=${searchQuery.author }">»</a></li>
					<li><a
						href="searchBook?page=${noOfPages}&isbn=${searchQuery.isbn }&title=${searchQuery.title }&author=${searchQuery.author }">Last</a></li>
				</c:if>

			</ul>
			<div id="source-button" class="btn btn-primary btn-xs"
				style="display: none;">&lt; &gt;</div>
		</div>
		<!-- /example -->
	</div>
</body>
</html>