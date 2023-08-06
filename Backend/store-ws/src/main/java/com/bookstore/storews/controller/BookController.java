package com.bookstore.storews.controller;

import org.springframework.web.bind.annotation.*;
import com.bookstore.storews.book.*;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping("books")
public class BookController {

	@RequestMapping(method = RequestMethod.GET, path = "/getAllBooks")
	public ArrayList<Book> getAllBooks() {
		ArrayList<Book> bookList = new ArrayList<>();
		try {
			BookDao bookDao = new BookDao();
			bookList = bookDao.readAllBooks();
			System.out.print(bookList);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return bookList;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getBookById/{bookid}")
	public Book getBookById(@PathVariable("bookid") String bookid) {
		Book book = new Book();
		try {
			BookDao bookDao = new BookDao();
			book = bookDao.getBookById(bookid);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return book;
	}

	@RequestMapping(path = "/createBook", consumes = "application/json", method = RequestMethod.POST)
	public boolean createBook(@RequestBody Book book) {
		boolean created = false;
		try {
			BookDao bookDao = new BookDao();
			created = bookDao.createBook(book);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return created;
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/deleteBook/{bookid}")
	public boolean deleteBook(@PathVariable("bookid") String bookid) {
		boolean deleted = false;
		try {
			BookDao bookDao = new BookDao();
			deleted = bookDao.deleteBook(bookid);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return deleted;
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = "application/json", path = "/updateBook")
	public boolean updateBook(@RequestBody Book book) {
		boolean updated = false;
		try {
			BookDao bookDao = new BookDao();
			updated = bookDao.updateBook(book);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return updated;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getTopSellingBooks/{limit}")
	public ArrayList<Book> getTopSellingBooks(@PathVariable("limit") int limit) {
		ArrayList<Book> topSellingBooks = new ArrayList<>();
		try {
			BookDao bookDao = new BookDao();
			topSellingBooks = bookDao.getTopSellingBooks(limit);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return topSellingBooks;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getNewestBooks/{limit}")
	public ArrayList<Book> getNewestBooks(@PathVariable("limit") int limit) {
		ArrayList<Book> newestBooks = new ArrayList<>();
		try {
			BookDao bookDao = new BookDao();
			newestBooks = bookDao.getNewestBooks(limit);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return newestBooks;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getHighestRatedBooks/{limit}")
	public ArrayList<Book> getHighestRatedBooks(@PathVariable("limit") int limit) {
		ArrayList<Book> highestRatedBooks = new ArrayList<>();
		try {
			BookDao bookDao = new BookDao();
			highestRatedBooks = bookDao.getHighestRatedBooks(limit);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return highestRatedBooks;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getLowestStockBooks/{limit}")
	public ArrayList<Book> getLowestStockBooks(@PathVariable("limit") int limit) {
		ArrayList<Book> lowestStockBooks = new ArrayList<>();
		try {
			BookDao bookDao = new BookDao();
			lowestStockBooks = bookDao.getLowestStockBooks(limit);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return lowestStockBooks;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/searchBooks/{keyword}", consumes = "application/json")
	public ArrayList<Book> searchBooks(@PathVariable("keyword") String keyword) {
		ArrayList<Book> books = new ArrayList<>();
		try {
			BookDao bookDao = new BookDao();
			books = bookDao.searchBooks(keyword);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return books;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getFilteredBooks", consumes = "application/json")
	public ArrayList<Book> getFilteredBooks(@RequestParam(value = "genre", required = false) String[] genreIds,
			@RequestParam(value = "price", required = false) double price) {
		ArrayList<Book> filteredBooks = new ArrayList<>();
		try {
			BookDao bookDao = new BookDao();
			filteredBooks = bookDao.getFilteredBooks(genreIds, price);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return filteredBooks;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getTotalRevenue")
	public double getTotalRevenue() {
		double totalRevenue = 0;
		try {
			BookDao bookDao = new BookDao();
			totalRevenue = bookDao.getTotalRevenue();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return totalRevenue;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getTotalTypeOfBooks")
	public int getTotalTypeOfBooks() {
		int totalBooks = 0;
		try {
			BookDao bookDao = new BookDao();
			totalBooks = bookDao.getTotalTypeOfBooks();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return totalBooks;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getTotalBooks")
	public int getTotalBooks() {
		int totalBooks = 0;
		try {
			BookDao bookDao = new BookDao();
			totalBooks = bookDao.getTotalBooks();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return totalBooks;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getTotalBooksSold")
	public int getTotalBooksSold() {
		int totalBooksSold = 0;
		try {
			BookDao bookDao = new BookDao();
			totalBooksSold = bookDao.getTotalBooksSold();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return totalBooksSold;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/getAverageRatingOfAllBooks")
	public double getAverageRatingOfAllBooks() {
		double averageRating = 0;
		try {
			BookDao bookDao = new BookDao();
			averageRating = bookDao.getAverageRatingOfAllBooks();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return averageRating;
	}
}
