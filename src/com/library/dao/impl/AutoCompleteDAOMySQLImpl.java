package com.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import com.library.dao.AutoCompleteDAO;
import com.library.util.Constants;

public class AutoCompleteDAOMySQLImpl implements AutoCompleteDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;

	@Override
	public ArrayList<String> autoCompleteTitle(String q) {
		// TODO Auto-generated method stub
		ArrayList<String> arrSearchResults = new ArrayList<String>();

		Set<String> hs = new TreeSet<>();

		String title;
		int count = 0;
		int remaining = 0;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "SELECT SQL_CALC_FOUND_ROWS title from book where lower(title) like lower(?) limit " + Constants.AUTO_COMPLETE_LIMIT;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, q+"%");

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				title = set.getString(1);

				arrSearchResults.add(title);
			}

			set.close();

			set = pstmt.executeQuery("SELECT FOUND_ROWS()");
			if(set.next())
				count = set.getInt(1);

			set.close();

			if(count < Constants.AUTO_COMPLETE_LIMIT) {
				remaining = Constants.AUTO_COMPLETE_LIMIT - count;

				sql = "SELECT title from book where lower(title) like lower(?) limit " + remaining;
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+q+"%");

				set = pstmt.executeQuery();

				while(set.next()){
					//Retrieve by column name
					title = set.getString(1);

					arrSearchResults.add(title);
				}
			}

		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		hs.addAll(arrSearchResults);
		arrSearchResults.clear();
		arrSearchResults.addAll(hs);

		return arrSearchResults;
	}

	@Override
	public ArrayList<String> autoCompleteAuthor(String q) {
		// TODO Auto-generated method stub
		Set<String> hs = new TreeSet<>();

		ArrayList<String> arrSearchResults = new ArrayList<String>();
		String title;
		int count = 0;
		int remaining = 0;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "SELECT SQL_CALC_FOUND_ROWS distinct author_name from book_authors where lower(author_name) like lower(?) limit " + Constants.AUTO_COMPLETE_LIMIT;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, q+"%");

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				title = set.getString(1);

				arrSearchResults.add(title);
			}

			set.close();

			set = pstmt.executeQuery("SELECT FOUND_ROWS()");
			if(set.next())
				count = set.getInt(1);

			set.close();

			if(count < Constants.AUTO_COMPLETE_LIMIT) {
				remaining = Constants.AUTO_COMPLETE_LIMIT - count;

				sql = "SELECT distinct author_name from book_authors where lower(author_name) like lower(?) limit " + remaining;
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+q+"%");

				set = pstmt.executeQuery();

				while(set.next()){
					//Retrieve by column name
					title = set.getString(1);

					arrSearchResults.add(title);
				}
			}

		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		hs.addAll(arrSearchResults);
		arrSearchResults.clear();
		arrSearchResults.addAll(hs);

		return arrSearchResults;
	}

	@Override
	public ArrayList<String> autoCompleteIsbn(String q) {
		// TODO Auto-generated method stub
		Set<String> hs = new TreeSet<>();

		ArrayList<String> arrSearchResults = new ArrayList<String>();
		String isbn;
		int count = 0;
		int remaining = 0;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "SELECT SQL_CALC_FOUND_ROWS distinct book_id from book_loans_vw where lower(book_id) like lower(?) limit " + Constants.AUTO_COMPLETE_LIMIT;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, q+"%");

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				isbn = set.getString(1);

				arrSearchResults.add(isbn);
			}

			set.close();

			set = pstmt.executeQuery("SELECT FOUND_ROWS()");
			if(set.next())
				count = set.getInt(1);

			set.close();

			if(count < Constants.AUTO_COMPLETE_LIMIT) {
				remaining = Constants.AUTO_COMPLETE_LIMIT - count;

				sql = "SELECT distinct name from book_loans_vw where lower(name) like lower(?) limit " + remaining;
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+q+"%");

				set = pstmt.executeQuery();

				while(set.next()){
					//Retrieve by column name
					isbn = set.getString(1);

					arrSearchResults.add(isbn);
				}
			}

		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		hs.addAll(arrSearchResults);
		arrSearchResults.clear();
		arrSearchResults.addAll(hs);

		return arrSearchResults;
	}

	@Override
	public ArrayList<String> autoCompleteName(String q) {
		// TODO Auto-generated method stub
		Set<String> hs = new TreeSet<>();

		ArrayList<String> arrSearchResults = new ArrayList<String>();
		String name;
		int count = 0;
		int remaining = 0;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "SELECT SQL_CALC_FOUND_ROWS distinct name from book_loans_vw where lower(name) like lower(?) limit " + Constants.AUTO_COMPLETE_LIMIT;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, q+"%");

			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				name = set.getString(1);

				arrSearchResults.add(name);
			}

			set.close();

			set = pstmt.executeQuery("SELECT FOUND_ROWS()");
			if(set.next())
				count = set.getInt(1);

			set.close();

			if(count < Constants.AUTO_COMPLETE_LIMIT) {
				remaining = Constants.AUTO_COMPLETE_LIMIT - count;

				sql = "SELECT distinct name from book_loans_vw where lower(name) like lower(?) limit " + remaining;
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+q+"%");

				set = pstmt.executeQuery();

				while(set.next()){
					//Retrieve by column name
					name = set.getString(1);

					arrSearchResults.add(name);
				}
			}

		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}

		hs.addAll(arrSearchResults);
		arrSearchResults.clear();
		arrSearchResults.addAll(hs);

		return arrSearchResults;
	}
}
