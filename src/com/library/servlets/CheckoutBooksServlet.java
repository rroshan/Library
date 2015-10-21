package com.library.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.library.bean.MessageBean;
import com.library.dao.CheckoutBookDAO;
import com.library.dao.impl.CheckoutBookDAOMySQLImpl;

/**
 * Servlet implementation class CheckoutBooksServlet
 */
@WebServlet("/CheckoutBooksServlet")
public class CheckoutBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckoutBooksServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bookStr, message = null;
		JSONObject bookObj = null;
		boolean available = false;
		boolean canBorrow = false;
		boolean success = true;

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String jsonString = request.getParameter("data");
		String borrowerId = request.getParameter("borrowerId");

		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(jsonString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONArray array = (JSONArray)obj;

		CheckoutBookDAO checkoutBookDAO = new CheckoutBookDAOMySQLImpl();
		boolean validBorrower = checkoutBookDAO.validateBorrower(Integer.parseInt(borrowerId));

		String msgStr = "";
		MessageBean msg = new MessageBean();

		if(!validBorrower) {
			msg.setMessage("Invalid Borrower ID");
			msg.setType("Fail");

			message = new Gson().toJson(msg);
			success = false;
		}
		else 
		{
			int pendingBooks = checkoutBookDAO.checkPendingBooks(Integer.parseInt(borrowerId));

			if(pendingBooks + array.size() > 3) {
				canBorrow = false;
			}
			else
			{
				canBorrow = true;
			}

			if(!canBorrow) 
			{
				msg.setMessage("Number of books borrowed exceeds the limit 3");
				msg.setType("Fail");

				message = new Gson().toJson(msg);
				success = false;
			} 
			else 
			{
				for(int i=0; i<array.size(); i++)
				{
					bookStr = (String) array.get(i);
					try {
						bookObj = (JSONObject) parser.parse(bookStr);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					available = checkoutBookDAO.checkBookAvailability(bookObj.get("isbn").toString(), Integer.parseInt(bookObj.get("branchId").toString()));

					if(!available) 
					{
						msgStr = bookObj.get("title").toString() + " is not available in branch " + bookObj.get("branchId").toString() + "<br>";
						if(msg.getType() == null) {
							msg.setType("Fail");
							msg.setMessage(msgStr);
							success = false;
						}
						else
						{
							msg.appendMessage(msgStr);
						}
					} 
					available = true;
				}
				
				if(!success)
					message = new Gson().toJson(msg);
			}
		}

		if(success)
		{
			for(int i=0; i<array.size(); i++)
			{
				bookStr = (String) array.get(i);
				try {
					bookObj = (JSONObject) parser.parse(bookStr);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				checkoutBookDAO.checkoutBook(bookObj.get("isbn").toString(), Integer.parseInt(borrowerId), Integer.parseInt(bookObj.get("branchId").toString()));
			}
			
			msg.setMessage("Checkout Successful");
			msg.setType("Success");

			message = new Gson().toJson(msg);
		}
		
		response.getWriter().write(message);
	}
}
