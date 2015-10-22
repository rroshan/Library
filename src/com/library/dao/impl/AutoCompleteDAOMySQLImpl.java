package com.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

		return arrSearchResults;
	}

	@Override
	public ArrayList<String> autoCompleteAuthor(String q) {
		// TODO Auto-generated method stub
		ArrayList<String> arrSearchResults = new ArrayList<String>();
		String title;
		int count = 0;
		int remaining = 0;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "SELECT SQL_CALC_FOUND_ROWS author_name from book_authors where lower(author_name) like lower(?) limit " + Constants.AUTO_COMPLETE_LIMIT;
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
				
				sql = "SELECT author_name from book_authors where lower(author_name) like lower(?) limit " + remaining;
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

		return arrSearchResults;
	}

}
