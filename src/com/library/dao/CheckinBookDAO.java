package com.library.dao;

import java.sql.Date;
import java.util.ArrayList;

import com.library.bean.CheckinBookResultBean;

public interface CheckinBookDAO {
	public ArrayList<CheckinBookResultBean> searchBookLoansByCardNo(int cardNo);
	
	public ArrayList<CheckinBookResultBean> searchBookLoansByName(String name);
	
	public ArrayList<CheckinBookResultBean> searchBookLoansByISBN(String isbn);
	
	public ArrayList<CheckinBookResultBean> searchBookloansByCardNoAndISBN(int cardNo, String isbn);
	
	public ArrayList<CheckinBookResultBean> searchBookloansByNameAndISBN(String name, String isbn);
	
	public void updateBookDateIn(int loanId, Date dt);
}
