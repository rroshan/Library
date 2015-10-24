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
		if(this.cardNo.isEmpty()) {
			if(!this.name.isEmpty() && !this.isbn.isEmpty())
			{
				return dao.searchBookloansByNameAndISBN(this.name, this.isbn);
			}
			else if(this.name.isEmpty() && !this.isbn.isEmpty())
			{
				return dao.searchBookLoansByISBN(this.isbn);
			}
			else
			{
				return dao.searchBookLoansByName(this.name);
			}
		}
		else
		{
			if(this.isbn.isEmpty())
			{
				return dao.searchBookLoansByCardNo(Integer.parseInt(this.cardNo));
			}
			else
			{
				return dao.searchBookloansByCardNoAndISBN(Integer.parseInt(this.cardNo), this.isbn);
			}
		}
	}
}
