package com.library.bean;

import java.sql.Date;

public class SearchFinesResultsBean {
	private String bookId;
	private int branchId;
	private Date dueDate;
	private int lateDays;
	private double fineAmt;
	private String status;
	
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public int getLateDays() {
		return lateDays;
	}
	public void setLateDays(int lateDays) {
		this.lateDays = lateDays;
	}
	public double getFineAmt() {
		return fineAmt;
	}
	public void setFineAmt(double fineAmt) {
		this.fineAmt = fineAmt;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
