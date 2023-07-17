package controller;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import Books.*;

@RestController
@RequestMapping("books")

public class BookController {
	
	@RequestMapping(method=RequestMethod.GET, path="/getAllBooks")
	public ArrayList<Book> getAllBooks() {
		ArrayList<Book> bookList = new ArrayList<>();
		try {
			BookDao book = new BookDao();
			bookList = book.readAllBooks();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return bookList;
	}
	
}
