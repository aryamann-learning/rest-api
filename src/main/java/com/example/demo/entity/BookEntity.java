package com.example.demo.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.example.demo.dto.Book;

@Entity
@Table(name = "books")
public class BookEntity {
	private long isbn;
	private String bookName;
	private String authorName;
	private String bookType;
	private Date releaseDate;

	public BookEntity() {
	}

	public BookEntity(String bookName, String authorName, String booktype, Date releasedate) {
		this.bookName = bookName;
		this.authorName = authorName;
		this.bookType = booktype;
		this.releaseDate = releasedate;
	}

	@Id
	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Book toDto() {
		Book book = new Book();
		book.setAuthorName(this.authorName);
		book.setBookName(this.bookName);
		book.setBookType(this.bookType);
		book.setIsbn(this.isbn);
		book.setReleaseDate(this.releaseDate);
		return book;

	}

}
