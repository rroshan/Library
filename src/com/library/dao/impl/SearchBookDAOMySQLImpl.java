package com.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.library.bean.SearchBookResultBean;
import com.library.dao.SearchBookDAO;
import com.library.dao.impl.ConnectionFactory;
import com.library.bo.Book;

public class SearchBookDAOMySQLImpl implements SearchBookDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;

	private ArrayList<String> getBookIDFromTitle(String title) {
		ArrayList<String> book_ids = new ArrayList<String>();
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "SELECT book_id from book where lower(title) like lower(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+title+"%");

			set = pstmt.executeQuery();

			while(set.next()) {
				book_ids.add(set.getString(1));
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return book_ids;
	}

	private ArrayList<String> getBookIDFromAuthor(String author) {
		ArrayList<String> book_ids = new ArrayList<String>();
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "SELECT book_id from book_authors where lower(author_name) like lower(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+author+"%");

			set = pstmt.executeQuery();

			while(set.next()) {
				book_ids.add(set.getString(1));
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return book_ids;
	}	

	@Override
	public ArrayList<SearchBookResultBean> searchBooksByISBN(String isbn) {
		ArrayList<SearchBookResultBean> arrSearchResults = new ArrayList<SearchBookResultBean>();
		SearchBookResultBean searchResult;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "SELECT s.book_id, s.title, s.branch_id, s.no_of_copies, s.remaining_books, a.authors, s.cover FROM (SELECT book_id, GROUP_CONCAT(author_name SEPARATOR ',') AS authors FROM book_authors GROUP BY book_id) a, search_vw s WHERE s.book_id = ? AND s.book_id = a.book_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, isbn);

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				Book book = new Book();
				book.setIsbn(set.getString(1));
				book.setTitle(set.getString(2));
				book.setAuthors(set.getString(6));
				book.setCover(set.getString(7));

				searchResult = new SearchBookResultBean();
				searchResult.setBook(book);

				searchResult.setBranchId(set.getInt(3));
				searchResult.setNoOfCopiesBranch(set.getInt(4));
				searchResult.setAvailableCopiesBranch(set.getInt(5));
				



				arrSearchResults.add(searchResult);
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return arrSearchResults;
	}

	@Override
	public ArrayList<SearchBookResultBean> searchBooksByTitle(String title) {
		// TODO Auto-generated method stub
		ArrayList<String> book_ids = getBookIDFromTitle(title);
		ArrayList<SearchBookResultBean> arrResult = new ArrayList<SearchBookResultBean>();
		ArrayList<SearchBookResultBean> arrSubResult = new ArrayList<SearchBookResultBean>();

		Iterator<String> it = book_ids.iterator();
		String book_id;
		while(it.hasNext()) {
			book_id = it.next();

			arrSubResult.clear();

			arrSubResult = searchBooksByISBN(book_id);

			arrResult.addAll(arrSubResult);
		}

		return arrResult;
	}

	@Override
	public ArrayList<SearchBookResultBean> searchBooksByAuthor(String author) {
		// TODO Auto-generated method stub
		ArrayList<String> book_ids = getBookIDFromAuthor(author);
		ArrayList<SearchBookResultBean> arrResult = new ArrayList<SearchBookResultBean>();
		ArrayList<SearchBookResultBean> arrSubResult = new ArrayList<SearchBookResultBean>();

		Iterator<String> it = book_ids.iterator();
		String book_id;
		while(it.hasNext()) {
			book_id = it.next();

			arrSubResult.clear();

			arrSubResult = searchBooksByISBN(book_id);

			arrResult.addAll(arrSubResult);
		}

		return arrResult;
	}
}
