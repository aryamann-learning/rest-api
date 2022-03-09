package com.example.demo.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.comparator.SortByauthorName;
import com.example.demo.constants.DemoConstants;
import com.example.demo.dto.Book;
import com.example.demo.entity.BookEntity;
import com.example.demo.repository.BookRepository;
import com.example.demo.utils.DateUtils;

@Service
public class BookServiceImpl implements BookService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	DateFormat dateFormat = new SimpleDateFormat(DemoConstants.SRC_DATE_FORMATTER);

	@Autowired
	BookRepository bookRepository;

	@Override
	public List<Book> getBooks(String source) {
		List<Book> books = null;
		switch (source) {
		case DemoConstants.EXCEL:
			// call excel method
			books = getBooksFromExcel();
			break;
		case DemoConstants.TEXT:
			// call text method
			books = getBooksFromText();
			break;
		case DemoConstants.DB:
			// call db method
			books = getBooksFromDb();
			break;
		default:
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Source");
		}

		return books;
	}

	private List<Book> getBooksFromExcel() {
		List<Book> books = new ArrayList<>();
		File file = new File(DemoConstants.BOOK_EXCEL_PATH);
		try (FileInputStream fis = new FileInputStream(file); XSSFWorkbook wb = new XSSFWorkbook(fis);) {
			XSSFSheet sheet = wb.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();
			iterator.next();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				long isbn = (long) row.getCell(0).getNumericCellValue();
				String bookName = row.getCell(1).getStringCellValue();
				String authorName = row.getCell(2).getStringCellValue();
				String bookType = row.getCell(3).getStringCellValue();
				Date releaseDate = DateUtils.toDate(row.getCell(4).getStringCellValue(),
						DemoConstants.SRC_DATE_FORMATTER, DemoConstants.DES_DATE_FORMATTER);
				Book book = new Book();
				book.setIsbn(isbn);
				book.setBookName(bookName);
				book.setAuthorName(authorName);
				book.setBookType(bookType);
				book.setReleaseDate(releaseDate);
				books.add(book);
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while processing excel file");
		}
		Collections.sort(books, new SortByauthorName());

		return books;

	}

	private List<Book> getBooksFromText() {
		List<Book> books = new ArrayList<>();
		File file = new File(DemoConstants.BOOK_TEXT_PATH);
		try (FileReader fileReader = new FileReader(file); BufferedReader br = new BufferedReader(fileReader);) {

			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] book = line.split(File.separator + DemoConstants.PIPE_SEPERATOR);
				Book bookObj = new Book();
				bookObj.setIsbn(Integer.parseInt(book[0]));
				bookObj.setBookName(book[1]);
				bookObj.setAuthorName(book[2]);
				bookObj.setBookType(book[3]);
				bookObj.setReleaseDate(
						DateUtils.toDate(book[4], DemoConstants.SRC_DATE_FORMATTER, DemoConstants.DES_DATE_FORMATTER));
				books.add(bookObj);
				line = br.readLine();
			}
			Collections.sort(books, new SortByauthorName());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while processing text file");

		}
		return books;
	}

	private List<Book> getBooksFromDb() {

		List<BookEntity> list = bookRepository.findAll(Sort.by(Direction.ASC, "authorName"));
		List<Book> books = new ArrayList<>();
		for (BookEntity bookEntity : list) {
			books.add(bookEntity.toDto());
		}
		return books;
	}

	@Override
	public void updateBook(String source, Long isbn, Book book) {
		switch (source) {
		case DemoConstants.EXCEL:
			updateBookInExcel(isbn, book);
			break;
		case DemoConstants.TEXT:
			updateBookInText(isbn, book);
			break;
		case DemoConstants.DB:
			updateBookInDb(isbn, book);
			break;
		default:
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Source");
		}
	}

	private void updateBookInText(Long isbn, Book book) {
		// TODO: will implement text

	}

	private void updateBookInExcel(Long isbn, Book book) {
		// TODO: will implement excel
	}

	private void updateBookInDb(Long isbn, Book book) {
		if (!isbn.equals(book.getIsbn())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Pathvariable isbn doesn't match requestbody isbn");
		} else {
			Optional<BookEntity> bookEntity = bookRepository.findById(isbn);
			if (bookEntity.isPresent()) {
				BookEntity enBook = new BookEntity();
				enBook.setIsbn(book.getIsbn());
				enBook.setAuthorName(book.getAuthorName());
				enBook.setBookName(book.getBookName());
				enBook.setBookType(book.getBookType());
				enBook.setReleaseDate(book.getReleaseDate());
				bookRepository.save(enBook);
				System.out.println();
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "isbn does not exist");
			}
		}
	}

	@Override
	public Long createBook(Book book, String source) {
		Long isbn = null;
		switch (source) {
		case DemoConstants.DB:
			isbn = saveToDb(book);
			break;
		case DemoConstants.TEXT:
			isbn = saveToText(book);
			break;
		case DemoConstants.EXCEL:
			isbn = saveToExcel(book);
			break;
		default:
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Source");
		}
		return isbn;
	}

	private Long saveToExcel(Book book) {
		File file = new File(DemoConstants.BOOK_EXCEL_PATH);
		try (FileInputStream fis = new FileInputStream(file);
				XSSFWorkbook wb = new XSSFWorkbook(fis);
				FileOutputStream fos = new FileOutputStream(file);) {
			XSSFSheet sheet = wb.getSheetAt(0);
			int lastRow = sheet.getLastRowNum();
			Row row = sheet.createRow(++lastRow);
			addCellValue(row, book.getIsbn(), 0);
			addCellValue(row, book.getBookName(), 1);
			addCellValue(row, book.getAuthorName(), 2);
			addCellValue(row, book.getBookType(), 3);
			addCellValue(row, book.getReleaseDate(), 4);
			wb.write(fos);
			return book.getIsbn();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while processing excel file");
		}
	}

	private void addCellValue(Row row, Object obj, int index) {
		if (obj instanceof String)
			row.createCell(index).setCellValue((String) obj);

		else if (obj instanceof Integer)
			row.createCell(index).setCellValue((Integer) obj);

		else if (obj instanceof Long)
			row.createCell(index).setCellValue((Long) obj);

		else if (obj instanceof Date)
			row.createCell(index).setCellValue(dateFormat.format(obj));
	}

	private Long saveToText(Book book) {
		File file = new File(DemoConstants.BOOK_TEXT_PATH);

		try (FileWriter fileWriter = new FileWriter(file, true); BufferedWriter bw = new BufferedWriter(fileWriter);) {
			bw.write((String.valueOf(book.getIsbn())));
			bw.write(DemoConstants.PIPE_SEPERATOR);
			bw.write(book.getBookName());
			bw.write(DemoConstants.PIPE_SEPERATOR);
			bw.write(book.getAuthorName());
			bw.write(DemoConstants.PIPE_SEPERATOR);
			bw.write(book.getBookType());
			bw.write(DemoConstants.PIPE_SEPERATOR);
			bw.write(dateFormat.format(book.getReleaseDate()));
			return book.getIsbn();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while processing text file");
		}
	}

	private Long saveToDb(Book book) {
		BookEntity en = new BookEntity();
		en.setAuthorName(book.getAuthorName());
		en.setBookName(book.getBookName());
		en.setBookType(book.getBookType());
		en.setIsbn(book.getIsbn());
		en.setReleaseDate(book.getReleaseDate());
		bookRepository.save(en);
		return en.getIsbn();
	}

	@Override
	public void deleteBook(String source, Long isbn) {
		switch (source) {
		case DemoConstants.EXCEL:
			deleteFromExcel(isbn);
			break;
		case DemoConstants.TEXT:
			deleteFromText(isbn);
			break;
		case DemoConstants.DB:
			deleteFromDb(isbn);
			break;
		default:
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Source");
		}
	}

	private void deleteFromExcel(Long isbn) {

		// TODO: will implement excel
	}

	private void deleteFromText(Long isbn) {
		File file = new File(DemoConstants.BOOK_TEXT_PATH);
		File tempDb = new File(file.getAbsoluteFile() + "tmp");
		try (FileReader fileReader = new FileReader(file);
				FileWriter fileWriter = new FileWriter(tempDb, true);
				BufferedReader br = new BufferedReader(fileReader);
				BufferedWriter bw = new BufferedWriter(fileWriter)) {
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.contains(String.valueOf(isbn))) {
					continue;
				}
				bw.write(line);
				bw.flush();
				bw.newLine();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while processing text file");
		}
		file.delete();
		tempDb.renameTo(file);
	}

	private void deleteFromDb(Long isbn) {
		bookRepository.deleteById(isbn);
	}

}
