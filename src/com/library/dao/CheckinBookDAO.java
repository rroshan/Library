package com.library.dao;

import java.sql.Date;
import java.util.ArrayList;

import com.library.bean.CheckinBookResultBean;

public interface CheckinBookDAO {
	public ArrayList<CheckinBookResultBean> searchBookLoans(int cardNo, String name, String isbn);
	
	public void updateBookDateIn(int loanId, Date dt);
}
