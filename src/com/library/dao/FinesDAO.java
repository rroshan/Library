package com.library.dao;

import java.util.ArrayList;

import com.library.bean.SearchFinesResultsBean;

public interface FinesDAO {
	public int calculateFine();
	
	public ArrayList<SearchFinesResultsBean> searchFinesByBorrowerId(int borrowerId);
	
	public int payFines(int borrowerId);
}
