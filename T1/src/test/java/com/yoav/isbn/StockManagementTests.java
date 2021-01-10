package com.yoav.isbn;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
	
	@Test
	public void dbIsUsedIfDataIsPresent() {
		ExternalISBNDataService dbService = mock(ExternalISBNDataService.class);
		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
		
		when(dbService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));
		
		StockManager stockManager = new StockManager();
		stockManager.setWebService(webService);
		stockManager.setDBService(dbService);
		
		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		verify(dbService).lookup("0140177396");
		verify(webService, never()).lookup(anyString());
	}
	
	@Test
	public void webserviceIsUsedIfDataIsNotPresentInDB() {
		ExternalISBNDataService dbService = mock(ExternalISBNDataService.class);
		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
		
		when(dbService.lookup("0140177396")).thenReturn(null);
		when(webService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));
		
		StockManager stockManager = new StockManager();
		stockManager.setWebService(webService);
		stockManager.setDBService(dbService);
		
		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		verify(dbService).lookup("0140177396");
		verify(webService).lookup("0140177396");
	}
}
