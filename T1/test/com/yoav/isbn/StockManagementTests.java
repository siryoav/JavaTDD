package com.yoav.isbn;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StockManagementTests {

	@Test
	void testCanGetACorrectLocatorCode() {
		ExternalISBNDataService testWebService = new ExternalISBNDataService() {
			
			@Override
			public Book lookup(String isbn) {
				return new Book(isbn, "Of Mice And Men", "J. Stenbeck");
			}
		};
		
		ExternalISBNDataService testDBService = new ExternalISBNDataService() {
			
			@Override
			public Book lookup(String isbn) {
				return null;
			}
		};
		StockManager stockManager = new StockManager();
		stockManager.setWebService(testWebService);
		stockManager.setDBService(testDBService);
		
		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}

}
