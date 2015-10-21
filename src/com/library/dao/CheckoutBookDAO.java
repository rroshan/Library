package com.library.dao;

public interface CheckoutBookDAO {
	public boolean validateBorrower(int borrower_id);
	
	public int checkPendingBooks(int borrower_id);
	
	public boolean checkBookAvailability(String isbn, int branchId);
	
	public void checkoutBook(String isbn, int borrower_id, int branchId);
}
