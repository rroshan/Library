package com.library.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.library.bean.CheckinBookResultBean;
import com.library.dao.CheckinBookDAO;

public class CheckinBookDAOMySQLImpl implements CheckinBookDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;
	
	@Override
	public ArrayList<CheckinBookResultBean> searchBookLoans(int cardNo, String name, String isbn) {
		// TODO Auto-generated method stub
		
		ArrayList<CheckinBookResultBean> arrSearchResults = new ArrayList<CheckinBookResultBean>();
		CheckinBookResultBean searchResult;

		//getting database connection from connection pool
		//connection handled by tomcat
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "(SELECT * FROM book_loans_vw WHERE card_no = ?)" +
							" UNION " + 
						 "(SELECT * FROM book_loans_vw WHERE lower(name) = lower(?))" +
							" UNION " +
						 "(SELECT * FROM book_loans_vw WHERE lower(book_id) = lower(?))" +
							" UNION " +
						 "(SELECT * FROM book_loans_vw WHERE card_no = ? AND lower(book_id) = lower(?))" +
							" UNION " +
							"(SELECT * FROM book_loans_vw WHERE lower(name) = lower(?) AND lower(book_id) = lower(?))";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, cardNo);
			pstmt.setString(2, name);
			pstmt.setString(3, isbn);
			pstmt.setInt(4, cardNo);
			pstmt.setString(5, isbn);
			pstmt.setString(6, name);
			pstmt.setString(7, isbn);
			
			
			set = pstmt.executeQuery();

			while(set.next()){
				//Retrieve by column name
				searchResult = new CheckinBookResultBean();
				
				searchResult.setLoanId(set.getInt("loan_id"));
				searchResult.setIsbn(set.getString("book_id"));
				searchResult.setCardNo(set.getInt("card_no"));
				searchResult.setBranchId(set.getInt("branch_id"));
				searchResult.setName(set.getString("name"));
				searchResult.setDateOut(set.getDate("date_out"));
				searchResult.setDateDue(set.getDate("due_date"));

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
	public void updateBookDateIn(int loanId, Date dt) {
		// TODO Auto-generated method stub
		conn = ConnectionFactory.getConnection();
		
		try {
			String sql = "UPDATE book_loans SET date_in = ? WHERE loan_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setDate(1, dt);
			pstmt.setInt(2, loanId);
			
			pstmt.executeUpdate();
			
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
	}
	
	
}
