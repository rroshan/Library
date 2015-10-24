package com.library.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.library.dao.AutoCompleteDAO;
import com.library.dao.impl.AutoCompleteDAOMySQLImpl;

/**
 * Servlet implementation class AutoCompleteServlet
 */
@WebServlet("/AutoCompleteServlet")
public class AutoCompleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutoCompleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("application/json");
		ArrayList<String> results = null;
		String strResult;
		
		AutoCompleteDAO dao = new AutoCompleteDAOMySQLImpl();
		
		String term = request.getParameter("term");
		String type = request.getParameter("type");
		
		if(type.equalsIgnoreCase("title")) 
		{
			results = dao.autoCompleteTitle(term);
		}
		else if(type.equalsIgnoreCase("author"))
		{
			results = dao.autoCompleteAuthor(term);
		}
		else if(type.equalsIgnoreCase("name"))
		{
			results = dao.autoCompleteName(term);
		}
		else if(type.equalsIgnoreCase("isbn"))
		{
			results = dao.autoCompleteIsbn(term);
		}
		
		strResult = new Gson().toJson(results);
		
		response.getWriter().write(strResult);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
