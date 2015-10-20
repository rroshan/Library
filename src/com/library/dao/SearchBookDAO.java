package com.library.dao;

import java.util.ArrayList;
import com.library.bean.SearchBookResultBean;

public interface SearchBookDAO {
	public ArrayList<SearchBookResultBean> searchBooksByISBN(String isbn);
	
	public ArrayList<SearchBookResultBean> searchBooksByTitle(String title);
	
	public ArrayList<SearchBookResultBean> searchBooksByAuthor(String author);
}
