package com.library.dao;

import java.util.ArrayList;
import com.library.bean.SearchBookResultBean;

public interface SearchBookDAO {
	public ArrayList<SearchBookResultBean> searchBooksByISBN(String isbn, int offset, int noOfRecords);
	
	public ArrayList<SearchBookResultBean> searchBooksByTitle(String title, int offset, int noOfRecords);
	
	public ArrayList<SearchBookResultBean> searchBooksByAuthor(String author, int offset, int noOfRecords);

	public ArrayList<SearchBookResultBean> searchBooksByTitleAndAuthor(String author, String title, int offset, int noOfRecords);
	public int getNoOfRecords();
}
