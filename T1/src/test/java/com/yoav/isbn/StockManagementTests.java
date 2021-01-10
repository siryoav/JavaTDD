package com.yoav.isbn;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockManagementTests {
	
	ExternalISBNDataService testWebService;
	StockManager stockManager;
	ExternalISBNDataService testDBService;
	
	@BeforeEach
	public void setup() {
		testWebService = mock(ExternalISBNDataService.class);
		stockManager = new StockManager();
		stockManager.setWebService(testWebService);
		testDBService = mock(ExternalISBNDataService.class);
		stockManager.setDBService(testDBService);
	}

	@Test
	void testCanGetACorrectLocatorCode() {;
		when(testWebService.lookup(anyString())).thenReturn(new Book("0140177396", "Of Mice And Men", "J. Stenbeck"));
		when(testDBService.lookup(anyString())).thenReturn(null);
		
		stockManager.setDBService(testDBService);
		
		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}
	
	@Test
	public void dbIsUsedIfDataIsPresent() {
		when(testDBService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));
		
		String isbn = "0140177396";
		stockManager.getLocatorCode(isbn);
		verify(testDBService).lookup("0140177396");
		verify(testWebService, never()).lookup(anyString());
	}
	
	@Test
	public void webserviceIsUsedIfDataIsNotPresentInDB() {
		when(testDBService.lookup("0140177396")).thenReturn(null);
		when(testWebService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));
		
		stockManager.setWebService(testWebService);
		stockManager.setDBService(testDBService);
		
		String isbn = "0140177396";
		stockManager.getLocatorCode(isbn);
		verify(testDBService).lookup("0140177396");
		verify(testWebService).lookup("0140177396");
	}
}
