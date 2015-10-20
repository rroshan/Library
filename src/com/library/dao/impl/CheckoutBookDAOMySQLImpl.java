package com.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			String sql = "select count(*) from book_loans where card_no = ? and date_in is not null";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, borrower_id);

			set = pstmt.executeQuery();

			while(set.next()) {
				count = set.getInt(1);
			}
			
			if(count >= 3) {
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
		return false;
	}

	@Override
	public void checkoutBook(String isbn, int borrower_id, int branchId) {
		// TODO Auto-generated method stub

	}
}
