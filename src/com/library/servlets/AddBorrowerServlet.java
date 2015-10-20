package com.library.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String phone = request.getParameter("phone");
		
		Borrower b = new Borrower();
		b.setFname(fname);
		b.setLname(lname);
		b.setEmail(email);
		b.setAddress(address);
		b.setCity(city);
		b.setState(state);
		b.setPhone(phone);
		
		AddBorrowerDAO addBorrowerDAO = new AddBorrowerDAOMySQLImpl();
		addBorrowerDAO.insertBorrower(b);
		
		response.setCharacterEncoding("utf-8");
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/create_borrowe.jsp");
		rd.forward(request, response);
	}

}
