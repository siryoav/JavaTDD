package com.yoav.isbn;

public class ValidateISBN {

	private static final int LONG_ISBN_MULTIPLIER = 10;
	private static final int SHORT_ISBN_MULTIPLIER = 11;
	private static final int SHORT_ISBN_LENGTH = 10;
	private static final int LONG_ISBN_LENGTH = 13;

	public boolean checkISBN(String isbn) {
		if(isbn.length() == LONG_ISBN_LENGTH) {
			return isThisAValidLongDigitISBN(isbn);
		} else if(isbn.length() == SHORT_ISBN_LENGTH) {
			return isThisAValidShortDigitISBN(isbn);
		}

		throw new NumberFormatException("Not 10 or 13 digits");
	}

	private boolean isThisAValidShortDigitISBN(String isbn) {
		int total = 0;
		for (int i = 0; i < SHORT_ISBN_LENGTH; i++) {
			if(!Character.isDigit(isbn.charAt(i))) {
				if(i == SHORT_ISBN_LENGTH - 1 && isbn.charAt(i) == 'X') {
					total += SHORT_ISBN_LENGTH;
				} else {
					throw new NumberFormatException("ISBN numbers can be only digits");	
				}
			} else {
				total += Character.getNumericValue(isbn.charAt(i)) * (SHORT_ISBN_LENGTH - i);
			}
		}
		
		return(total % SHORT_ISBN_MULTIPLIER == 0);
	}

	private boolean isThisAValidLongDigitISBN(String isbn) {
		int total = 0;
		for (int i = 0; i < LONG_ISBN_LENGTH; i++) {
			if(i % 2 == 0) {
				total += Character.getNumericValue(isbn.charAt(i));					
			} else {
				total += Character.getNumericValue(isbn.charAt(i)) * 3;
			}
		}
		
		return(total % LONG_ISBN_MULTIPLIER == 0);
	}

}
