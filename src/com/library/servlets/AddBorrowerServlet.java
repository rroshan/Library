package com.library.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.library.bean.MessageBean;
import com.library.bo.Borrower;
import com.library.dao.AddBorrowerDAO;
import com.library.dao.impl.AddBorrowerDAOMySQLImpl;

/**
 * Servlet implementation class AddBorrowerServlet
 */
@WebServlet("/AddBorrowerServlet")
public class AddBorrowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddBorrowerServlet() {
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

		String message = null;
		
		String fname = request.getParameter("inputFname");
		String lname = request.getParameter("inputLname");
		String email = request.getParameter("inputEmail");
		String address = request.getParameter("inputStreetAddress");
		String city = request.getParameter("inputCity");
		String state = request.getParameter("inputState");
		String phone = request.getParameter("inputPhone");

		Borrower b = new Borrower();
		b.setFname(fname);
		b.setLname(lname);
		b.setEmail(email);
		b.setAddress(address);
		b.setCity(city);
		b.setState(state);
		b.setPhone(phone);

		AddBorrowerDAO addBorrowerDAO = new AddBorrowerDAOMySQLImpl();
		
		response.setCharacterEncoding("UTF-8");

		if(request.getParameterMap().containsKey("operation"))
		{
			boolean result = addBorrowerDAO.checkBorrowerExists(b);

			response.setContentType("application/json");
			
			MessageBean msg = new MessageBean();
			
			if(!result)
			{
				msg.setType("Fail");
				msg.setMessage("Borrower Already Exists");
			}
			else
			{
				msg.setType("Success");
				msg.setMessage("Can proceed to insert borrower");
			}
			
			message = new Gson().toJson(msg);
			response.getWriter().write(message);
		}
		else
		{
			addBorrowerDAO.insertBorrower(b);
			request.setAttribute("message", "Borrower successfully created");

			RequestDispatcher rd = getServletContext().getRequestDispatcher("/create_borrower.jsp");
			rd.forward(request, response);
		}
	}
}
