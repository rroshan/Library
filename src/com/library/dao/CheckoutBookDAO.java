package com.library.dao;

public interface CheckoutBookDAO {
	public boolean validateBorrower(String borrower_id);
	
	public boolean checkBookAvailability(String isbn, int branchId);
	
	public void checkoutBook(String isbn, String borrower_id, int branchId);
}
