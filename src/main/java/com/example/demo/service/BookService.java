package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.Book;

public interface BookService {
	public List<Book> getBooks(String source);

	public void updateBook(String source, Long isbn, Book book);

	public Long createBook(Book book, String source);

	public void deleteBook(String source, Long isbn);
}
