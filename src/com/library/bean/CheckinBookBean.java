package com.library.bean;

import java.util.ArrayList;

import com.library.dao.CheckinBookDAO;

public class CheckinBookBean {
	private String cardNo;
	private String name;
	private String isbn;
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public ArrayList<CheckinBookResultBean> searchCheckin(CheckinBookDAO dao) {
		int borrowerId;
		String isbn = null;
		String name = null;
		
		if(cardNo.isEmpty())
		{
			borrowerId = -1;
		}
		else
		{
			borrowerId = Integer.parseInt(cardNo);
		}
		
		if(this.isbn.isEmpty())
		{
			isbn = "#*#";
		}
		else
		{
			isbn = this.isbn;
		}
		
		if(this.name.isEmpty())
		{
			name = "#*#";
		}
		else
		{
			name = this.name;
		}
		
		return dao.searchBookLoans(borrowerId, name, isbn);
	}
}
