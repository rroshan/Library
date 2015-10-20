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
		String bookStr, message;
		JSONObject bookObj = null;
		boolean available = false;


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
		boolean canCheckOut = checkoutBookDAO.validateBorrower(borrowerId);

		if(!canCheckOut) {
			message = "{\"message\": \"User already has 3 books on Loan\"}";
			response.getWriter().write(message);
		} else {
			for(int i=0; i<array.size(); i++){
				bookStr = (String) array.get(i);
				try {
					bookObj = (JSONObject) parser.parse(bookStr);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				available = checkoutBookDAO.checkBookAvailability(bookObj.get("isbn").toString(), Integer.parseInt(bookObj.get("branchId").toString()));
				
				if(!available) {
					message = "{\"message\":" + bookObj.get("title").toString() + " is not available in branch" + bookObj.get("branchId").toString() + ".\"}";
					response.getWriter().write(message);
					return;
				} else {
					checkoutBookDAO.checkoutBook(bookObj.get("isbn").toString(), borrowerId, Integer.parseInt(bookObj.get("branchId").toString()));
				}
				
				available = false;
			}
		}
	}
}
