package com.library.bean;

import java.util.ArrayList;

import com.library.bean.SearchBookResultBean;
import com.library.dao.SearchBookDAO;
import com.library.dao.impl.SearchBookDAOMySQLImpl;

public class SearchBookBean {
	private String isbn;
	private String title;
	private String author;

	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public ArrayList<SearchBookResultBean> searchBooks(SearchBookDAO searchBookDAO, int offset, int noOfRecords) {
		if(this.isbn.isEmpty()) {
			if(!this.title.isEmpty()) {
				return searchBookDAO.searchBooksByTitle(this.title, offset, noOfRecords);
			}
			else if (this.title.isEmpty() && !this.author.isEmpty()) {
				return searchBookDAO.searchBooksByAuthor(this.author, offset, noOfRecords);
			}
		}
		//search with book
		return searchBookDAO.searchBooksByISBN(this.isbn, offset, noOfRecords);
	}
}
