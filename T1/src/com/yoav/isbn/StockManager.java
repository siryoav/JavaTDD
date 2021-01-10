package com.yoav.isbn;

public class StockManager {

	private ExternalISBNDataService webService;
	private ExternalISBNDataService dbService;
	
	public void setWebService(ExternalISBNDataService service) {
		this.webService = service;
	}
	
	public void setDBService(ExternalISBNDataService service) {
		this.dbService = service;
	}

	public String getLocatorCode(String isbn) {
		Book book = dbService.lookup(isbn);
		if(book == null) {			
			book = webService.lookup(isbn);
		}
		StringBuilder locator = new StringBuilder();
		locator.append(isbn.substring(isbn.length() - 4));
		locator.append(book.getAutor().substring(0,1));
		locator.append(book.getTitle().split(" ").length);
		return locator.toString();
	}

}
