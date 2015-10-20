package com.library.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.bean.SearchBookBean;
import com.library.dao.SearchBookDAO;
import com.library.dao.impl.SearchBookDAOMySQLImpl;

/**
 * Servlet implementation class SearchBookServlet
 */
@WebServlet("/SearchBookServlet")
public class SearchBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchBookServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		int page = 1;
		int recordsPerPage = 15;

		if(request.getParameter("page") != null)
			page = Integer.parseInt(request.getParameter("page"));

		String isbn = request.getParameter("isbn");
		String title = request.getParameter("title");
		String author = request.getParameter("author");

		SearchBookBean sbb = new SearchBookBean();
		sbb.setIsbn(isbn);
		sbb.setTitle(title);
		sbb.setAuthor(author);
		
		SearchBookDAO searchBookDAO = new SearchBookDAOMySQLImpl();

		request.setAttribute("searchResult", sbb.searchBooks(searchBookDAO, (page - 1) * recordsPerPage, recordsPerPage));
		
		int noOfRecords = searchBookDAO.getNoOfRecords();
		
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
		
		request.setAttribute("searchQuery", sbb);
		response.setCharacterEncoding("utf-8");
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/list_of_books.jsp");
		rd.forward(request, response);
	}
}
