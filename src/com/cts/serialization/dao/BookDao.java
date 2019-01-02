package com.cts.serialization.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cts.serialization.pojo.Book;
import com.cts.serialization.util.ConnectionUtils;

public class BookDao {


	public void addBook(Book book ) {
		PreparedStatement stmt = null;
		try {
			Connection con = ConnectionUtils.getConnection();
			stmt = con.prepareStatement("insert into book values(?,?,?,?,?)");
			stmt.setLong(1, book.getBookId());
			stmt.setString(2, book.getTitle());
			stmt.setDouble(3, book.getPrice());
			stmt.setInt(4, book.getVolume());
			stmt.setDate(5, java.sql.Date.valueOf(book.getPublishDate()));
			stmt.execute();
			System.out.println("Book added succesfully");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void deleteBook(long id) {
		PreparedStatement stmt = null;
		try {
			Connection con = ConnectionUtils.getConnection();
			stmt = con.prepareStatement("delete from book where bookid = ?");
			stmt.setLong(1, id);
			int count= stmt.executeUpdate();
			if(count > 0) {
				System.out.println("Book Deleted Successfully with id " + id);
			}
			else {
				System.out.println("Unable to delete Book with id "+ id);	
			}
		}
		catch(Exception e){
			System.out.println("Unable to delete Book with id "+ id);	
			e.printStackTrace();
		}
		finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void searchBook(long id) {
		PreparedStatement stmt = null;
		try {
			Connection con = ConnectionUtils.getConnection();
			stmt = con.prepareStatement("select * from book where bookid = ?");
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			boolean found = false;
			while(rs.next()){
				found = true;
				id  = rs.getLong("bookId");
				double price = rs.getDouble("price");
				String title = rs.getString("title");
				int volume = rs.getInt("volume");
				Date publishDate = rs.getDate("publishDate");
				System.out.format("\n%10s %15s %15s %15s %15s", "Id", "Price", "Title", "Volume", "Publish Date");
				System.out.format("\n%10d %15.2f %15s %15d %15s \n", id, price,title,
						volume, publishDate);

			}
			if (!found) {
				System.out.println("Unable to find Book with id "+ id);
			}
		}
		catch(Exception e) {
			System.out.println("Unable to find Book with id "+ id);	
			e.printStackTrace();
		}
		finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public List<Book> getAllBooks(){
		PreparedStatement stmt = null;
		List<Book> books = new ArrayList<>();
		try {
			Connection con = ConnectionUtils.getConnection();
			stmt = con.prepareStatement("select * from book");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Book book = new Book();
				book.setBookId(rs.getLong("bookId"));
				book.setPrice(rs.getDouble("price"));
				book.setTitle(rs.getString("title"));
				book.setVolume(rs.getInt("volume"));
				book.setPublishDate(rs.getDate("publishDate").toLocalDate());
				books.add(book);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return books;
	}
	
	public void sortBookByTitle() {
		List<Book> books =  getAllBooks();
		books.sort((Book b1, Book b2) -> b1.getTitle().compareTo(b2.getTitle()));
		if(books != null && !books.isEmpty()) {
			System.out.println("Available books sort by title");
			System.out.format("\n%10s %15s %15s %15s %15s", "Id", "Price", "Title", "Volume", "Publish Date");
			for(Book book : books) {
				System.out.format("\n%10d %15.2f %15s %15d %15s \n", book.getBookId(), book.getPrice(), book.getTitle(),
						book.getVolume(), book.getPublishDate());
			}
		}
	}
	
	public void sortBookByPublishDate() {
		List<Book> books =  getAllBooks();
		books.sort((Book b1, Book b2) -> b1.getPublishDate().compareTo(b2.getPublishDate()));
		if(books != null && !books.isEmpty()) {
			System.out.println("Available books sort by publish date");
			System.out.format("\n%10s %15s %15s %15s %15s", "Id", "Price", "Title", "Volume", "Publish Date");
			for(Book book : books) {
				System.out.format("\n%10d %15.2f %15s %15d %15s \n", book.getBookId(), book.getPrice(), book.getTitle(),
						book.getVolume(), book.getPublishDate());
			}
		}
	}
}
