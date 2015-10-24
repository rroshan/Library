package com.library.dao;

import com.library.bo.Borrower;

public interface AddBorrowerDAO {
	public int insertBorrower(Borrower b);
	
	public boolean checkBorrowerExists(Borrower b); 
}
