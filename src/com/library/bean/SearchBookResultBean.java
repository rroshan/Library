package com.library.bean;

import com.library.bo.Book;

public class SearchBookResultBean {
	private Book book;
	private int branchId;
	private int noOfCopiesBranch;
	private int availableCopiesBranch;
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public int getNoOfCopiesBranch() {
		return noOfCopiesBranch;
	}
	public void setNoOfCopiesBranch(int noOfCopiesBranch) {
		this.noOfCopiesBranch = noOfCopiesBranch;
	}
	public int getAvailableCopiesBranch() {
		return availableCopiesBranch;
	}
	public void setAvailableCopiesBranch(int availableCopiesBranch) {
		this.availableCopiesBranch = availableCopiesBranch;
	}
	
	
}
