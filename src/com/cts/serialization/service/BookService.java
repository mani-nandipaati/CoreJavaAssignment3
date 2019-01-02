package com.cts.serialization.service;

import java.util.List;

import com.cts.serialization.dao.BookDao;
import com.cts.serialization.pojo.Book;

public class BookService {
	
	BookDao bookDao = new BookDao();
	
	public void addBook(Book book ) {
		bookDao.addBook(book);
	}
	
	public void deleteBook(long id) {
		bookDao.deleteBook(id);
	}

	public void searchBook(long id) {
		bookDao.searchBook(id);
	}
	
	public List<Book> getAllBooks(){
		return bookDao.getAllBooks();
	}
	
	public void sortBookByTitle() {
		bookDao.sortBookByTitle();
	}
	
	public void sortBookByPublishDate() {
		bookDao.sortBookByPublishDate();
	}

}
