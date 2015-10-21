package com.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.library.dao.CheckoutBookDAO;

public class CheckoutBookDAOMySQLImpl implements CheckoutBookDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;

	@Override
	public boolean validateBorrower(int borrower_id) {
		// TODO Auto-generated method stub
		
		int count = 0;
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select count(*) from borrower where card_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, borrower_id);

			set = pstmt.executeQuery();

			while(set.next()) {
				count = set.getInt(1);
			}
			
			if(count < 1) {
				return false;
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
		return true;
	}

	@Override
	public boolean checkBookAvailability(String isbn, int branchId) {
		// TODO Auto-generated method stub
		
		int remainingBooks = 0;
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select remaining_books from search_vw where book_id = ? and branch_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setInt(2, branchId);

			set = pstmt.executeQuery();

			while(set.next()) {
				remainingBooks = set.getInt(1);
			}
			
			if(remainingBooks < 1) {
				return false;
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
		return true;
	}

	@Override
	public void checkoutBook(String isbn, int borrower_id, int branchId) {
		// TODO Auto-generated method stub
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "insert into book_loans(book_id, branch_id, card_no, date_out, due_date) values (?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setInt(2, branchId);
			pstmt.setInt(3, borrower_id);
			
			Date today = new Date();
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(today); 
			c.add(Calendar.DATE, 14);
			Date dt = c.getTime();
			
			java.sql.Date dateOut = new java.sql.Date(today.getTime());
			java.sql.Date dateDue = new java.sql.Date(dt.getTime());
			
			pstmt.setDate(4, dateOut);
			pstmt.setDate(5, dateDue);

			pstmt.executeUpdate();
			
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
	}

	@Override
	public int checkPendingBooks(int borrower_id) {
		// TODO Auto-generated method stub
		int count = 0;
		conn = ConnectionFactory.getConnection();

		try {
			String sql = "select count(*) from book_loans where card_no = ? and date_in is null";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, borrower_id);

			set = pstmt.executeQuery();

			while(set.next()) {
				count = set.getInt(1);
			}
		} catch(SQLException sqlex) {
			sqlex.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			ConnectionFactory.closeResources(set, pstmt, conn);
		}
		return count;
	}
}
