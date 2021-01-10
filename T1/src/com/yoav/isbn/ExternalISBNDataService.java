package com.yoav.isbn;

public interface ExternalISBNDataService {
	public Book lookup(String isbn);
}
