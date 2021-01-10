package com.yoav.isbn;

public class ValidateISBN {

	public boolean checkISBN(String isbn) {
		if(isbn.length() != 10) throw new NumberFormatException("Not 10 digits");
		int total = 0;
		for (int i = 0; i < 10; i++) {
			if(!Character.isDigit(isbn.charAt(i))) {
				throw new NumberFormatException("ISBN numbers can be only digits");
			}
			total += isbn.charAt(i) * (10 - i);
		}
		
		if(total % 11 == 0) {
			return true;
		} else {
			return false;
		}
		
	}

}
