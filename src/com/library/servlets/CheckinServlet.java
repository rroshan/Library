package com.library.servlets;

import java.io.IOException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.library.bean.CheckinBookBean;
import com.library.dao.CheckinBookDAO;
import com.library.dao.impl.CheckinBookDAOMySQLImpl;

/**
 * Servlet implementation class CheckinServlet
 */
@WebServlet("/CheckinServlet")
public class CheckinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckinServlet() {
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

		if(request.getParameterMap().containsKey("operation"))
		{
			int loanId = Integer.parseInt(request.getParameter("loanId"));

			CheckinBookDAO checkinDAO = new CheckinBookDAOMySQLImpl();

			Date dt = new Date();

			java.sql.Date sDt = new java.sql.Date(dt.getTime());
			checkinDAO.updateBookDateIn(loanId, sDt);
		}
		
		String isbn = request.getParameter("isbn");
		String cardNo = request.getParameter("borrower_id");
		String name = request.getParameter("name");

		CheckinBookBean checkinBean = new CheckinBookBean();
		checkinBean.setName(name);
		checkinBean.setCardNo(cardNo);
		checkinBean.setIsbn(isbn);

		CheckinBookDAO checkinDAO = new CheckinBookDAOMySQLImpl();

		request.setAttribute("searchQuery", checkinBean);
		request.setAttribute("searchResult", checkinBean.searchCheckin(checkinDAO));

		response.setCharacterEncoding("utf-8");
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/book_checkin.jsp");
		rd.forward(request, response);
	}

}
