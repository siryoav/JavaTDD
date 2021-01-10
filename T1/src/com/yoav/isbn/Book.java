package com.yoav.isbn;

public class Book {
	private String isbn;
	private String title;
	private String autor;
	
	public Book(String isbn, String title, String autor) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public String getAutor() {
		return autor;
	}
}
