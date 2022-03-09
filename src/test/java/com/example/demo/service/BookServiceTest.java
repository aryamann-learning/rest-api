package com.example.demo.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.SpringBootTestConfig;
import com.example.demo.dto.Book;

public class BookServiceTest extends SpringBootTestConfig {
	@Autowired
	BookService bookService;

	@Test
	public void testGetBooks() {
		List<Book> book = bookService.getBooks("db");
		Assert.assertNotNull(book);
	}
}
