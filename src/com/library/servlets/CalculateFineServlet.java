package com.library.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.library.bean.MessageBean;
import com.library.bean.SearchFinesBean;
import com.library.bean.SearchFinesResultsBean;
import com.library.dao.FinesDAO;
import com.library.dao.impl.FinesDAOMySQLImpl;

/**
 * Servlet implementation class CalculateFineServlet
 */
@WebServlet("/CalculateFineServlet")
public class CalculateFineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CalculateFineServlet() {
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

		ArrayList<SearchFinesResultsBean> searchResults;
		FinesDAO finesDAO = new FinesDAOMySQLImpl();
		response.setCharacterEncoding("UTF-8");
		MessageBean msg = new MessageBean();
		double total = 0;

		if(request.getParameterMap().containsKey("operation")) 
		{
			response.setContentType("application/json");

			if(request.getParameter("operation").equalsIgnoreCase("calculate"))
			{
				int status = finesDAO.calculateFine();

				if(status == 1)
				{
					msg.setType("Success");
					msg.setMessage("Fines calculated successfully");	
				}
				else
				{
					msg.setType("Fail");
					msg.setMessage("Calulate Fines Failed");				
				}
			}
			else if(request.getParameter("operation").equalsIgnoreCase("pay_fine"))
			{
				String borrowerId = request.getParameter("borrower_id");
				
				int result = finesDAO.payFines(Integer.parseInt(borrowerId));
				
				if(result == 1)
				{
					msg.setType("Success");
					msg.setMessage("Fines paid successfully");
				}
				else if(result == 0)
				{
					msg.setType("Fail");
					msg.setMessage("Patron must return all books before paying the fine");	
				}
			}

			String message = new Gson().toJson(msg);
			response.getWriter().write(message);
		}
		else
		{
			String borrowerId = request.getParameter("borrower_id");

			SearchFinesBean finesBean = new SearchFinesBean();
			finesBean.setBorrowerId(borrowerId);

			searchResults = finesBean.searchFines();

			Iterator<SearchFinesResultsBean> it = searchResults.iterator();
			while(it.hasNext())
			{
				total = total + it.next().getFineAmt();
			}

			request.setAttribute("searchResult", searchResults);
			request.setAttribute("searchQuery", finesBean);
			request.setAttribute("total", total);

			RequestDispatcher rd = getServletContext().getRequestDispatcher("/fines.jsp");
			rd.forward(request, response);
		}
	}
}
