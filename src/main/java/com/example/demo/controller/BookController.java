package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Book;
import com.example.demo.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	BookService bookService;

	@GetMapping("/{source}/info")
	public ResponseEntity<List<Book>> getBooks(@PathVariable("source") String source) {
		return new ResponseEntity<>(bookService.getBooks(source), HttpStatus.OK);

	}

	@PutMapping("/{source}/{isbn}")
	public ResponseEntity<?> updateBook(@PathVariable("source") String source, @PathVariable("isbn") Long isbn,
			@RequestBody Book book) {
		bookService.updateBook(source, isbn, book);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/{source}")
	public ResponseEntity<Long> createBook(@PathVariable("source") String source, @Valid @RequestBody Book book) {
		bookService.createBook(book, source);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("/{source}/{isbn}")
	public ResponseEntity<?> deleteBook(@PathVariable("source") String source, @PathVariable("isbn") Long isbn) {
		bookService.deleteBook(source, isbn);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
