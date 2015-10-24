package com.library.dao;

import java.util.ArrayList;

public interface AutoCompleteDAO {
	public ArrayList<String> autoCompleteTitle(String q);
	
	public ArrayList<String> autoCompleteAuthor(String q);
	
	public ArrayList<String> autoCompleteIsbn(String q);
	
	public ArrayList<String> autoCompleteName(String q);
}
