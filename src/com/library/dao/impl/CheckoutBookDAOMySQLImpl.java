package com.library.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.library.dao.CheckoutBookDAO;

public class CheckoutBookDAOMySQLImpl implements CheckoutBookDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet set;
	
	@Override
	public boolean validateBorrower(String borrower_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkBookAvailability(String isbn, int branchId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void checkoutBook(String isbn, String borrower_id, int branchId) {
		// TODO Auto-generated method stub
		
	}

}
