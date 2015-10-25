package com.library.bean;

import java.util.ArrayList;

import com.library.dao.FinesDAO;
import com.library.dao.impl.FinesDAOMySQLImpl;

public class SearchFinesBean {
	private String borrowerId;
	
	public String getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(String borrowerId) {
		this.borrowerId = borrowerId;
	}
	
	public ArrayList<SearchFinesResultsBean> searchFines() {
		FinesDAO dao = new FinesDAOMySQLImpl();
		ArrayList<SearchFinesResultsBean> searchResults = null;
		
		if(!this.borrowerId.isEmpty())
		{
			searchResults = dao.searchFinesByBorrowerId(Integer.parseInt(this.borrowerId));
		}
		
		return searchResults;
	}
}
