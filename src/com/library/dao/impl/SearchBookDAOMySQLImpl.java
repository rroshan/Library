package com.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.library.bean.SearchBookResultBean;
import com.library.dao.SearchBookDAO;
import com.library.dao.impl.ConnectionFactory;
import com.library.bo.Book;

public class SearchBookDAOMySQLImpl implements SearchBookDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;
	private int noOfRecords;

	@Override
	public ArrayList<SearchBookResultBean> searchBooksByISBN(String isbn, int offset, int noOfRecords) {
		ArrayList<SearchBookResultBean> arrSearchResults = new ArrayList<SearchBookResultBean>();
		SearchBookResultBean searchResult;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "SELECT SQL_CALC_FOUND_ROWS s.book_id, s.title, s.branch_id, s.no_of_copies, s.remaining_books, a.authors, s.cover FROM (SELECT book_id, GROUP_CONCAT(author_name SEPARATOR ',') AS authors FROM book_authors GROUP BY book_id) a, search_vw s WHERE s.book_id = ? AND s.book_id = a.book_id order by s.book_id limit " + offset + ", " + noOfRecords;
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
			
			set.close();

			set = pstmt.executeQuery("SELECT FOUND_ROWS()");
			if(set.next())
				this.noOfRecords = set.getInt(1);
			
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
	public ArrayList<SearchBookResultBean> searchBooksByTitle(String title, int offset, int noOfRecords) {
		ArrayList<SearchBookResultBean> arrSearchResults = new ArrayList<SearchBookResultBean>();
		SearchBookResultBean searchResult;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "SELECT SQL_CALC_FOUND_ROWS s.book_id, s.title, s.branch_id, s.no_of_copies, s.remaining_books, a.authors, s.cover FROM (SELECT book_id, GROUP_CONCAT(author_name SEPARATOR ',') AS authors FROM book_authors GROUP BY book_id) a, search_vw s WHERE s.book_id in (SELECT book_id from book where lower(title) like lower(?)) AND s.book_id = a.book_id order by s.book_id limit " + offset + ", " + noOfRecords;

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+title+"%");

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

			set.close();

			set = pstmt.executeQuery("SELECT FOUND_ROWS()");
			if(set.next())
				this.noOfRecords = set.getInt(1);

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
	public ArrayList<SearchBookResultBean> searchBooksByAuthor(String author, int offset, int noOfRecords) {
		ArrayList<SearchBookResultBean> arrSearchResults = new ArrayList<SearchBookResultBean>();
		SearchBookResultBean searchResult;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "SELECT SQL_CALC_FOUND_ROWS s.book_id, s.title, s.branch_id, s.no_of_copies, s.remaining_books, a.authors, s.cover FROM (SELECT book_id, GROUP_CONCAT(author_name SEPARATOR ',') AS authors FROM book_authors GROUP BY book_id) a, search_vw s WHERE s.book_id in (SELECT book_id from book_authors where lower(author_name) like lower(?)) AND s.book_id = a.book_id order by s.book_id limit " + offset + ", " + noOfRecords;

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+author+"%");

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

			set.close();

			set = pstmt.executeQuery("SELECT FOUND_ROWS()");
			if(set.next())
				this.noOfRecords = set.getInt(1);

		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return arrSearchResults;
	}

	public int getNoOfRecords() {
		return this.noOfRecords;
	}

	@Override
	public ArrayList<SearchBookResultBean> searchBooksByTitleAndAuthor(
			String author, String title, int offset, int noOfRecords) {
		ArrayList<SearchBookResultBean> arrSearchResults = new ArrayList<SearchBookResultBean>();
		SearchBookResultBean searchResult;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "SELECT SQL_CALC_FOUND_ROWS s.book_id, s.title, s.branch_id, s.no_of_copies, s.remaining_books, a.authors, s.cover FROM (SELECT book_id, GROUP_CONCAT(author_name SEPARATOR ',') AS authors FROM book_authors GROUP BY book_id) a, search_vw s WHERE s.book_id in (select b.book_id from book b, book_authors ba where b.book_id = ba.book_id and lower(b.title) like lower(?) and lower(ba.author_name) like lower(?)) AND s.book_id = a.book_id order by s.book_id limit " + offset + ", " + noOfRecords;

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+title+"%");
			pstmt.setString(2, "%"+author+"%");

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

			set.close();

			set = pstmt.executeQuery("SELECT FOUND_ROWS()");
			if(set.next())
				this.noOfRecords = set.getInt(1);

		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		return arrSearchResults;
	}
}
