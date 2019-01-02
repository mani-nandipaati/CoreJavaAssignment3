package com.cts.serialization.main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.cts.serialization.pojo.Book;
import com.cts.serialization.pojo.Subject;
import com.cts.serialization.service.BookService;
import com.cts.serialization.service.SubjectService;

public class SerializationMainClass {
	static Scanner in = null;

	public static void main(String[] args) {
		String choice;
		BookService booKService = new BookService();
		SubjectService subjectService = new SubjectService();
		try {
			in = new Scanner(System.in);
			do {
				long id = 0;
				System.out.println();
				System.out.println("a.  Add a Subject");
				System.out.println("b.  Add a Book");
				System.out.println("c.  Delete a Subject");
				System.out.println("d.  Delete a book");
				System.out.println("e.  Search for a book");
				System.out.println("f.  Search for a subject");
				System.out.println("g.  Sort Book By Title");
				System.out.println("h.  Sort Subject By Subject Title");
				System.out.println("i.  Sort Books by publish Date");
				System.out.println("j.  Exit");
				System.out.println("--------------------------");
				System.out.println("Enter your choice");
				choice = in.next();    

				switch(choice) {
				case "a":
					List<Book> books = booKService.getAllBooks();
					Subject subject = createSubject(books);
					subjectService.addSubject(subject);
					break;
				case "b":
					Book book = createBook();
					booKService.addBook(book);
					break;
				case "c":
					System.out.println("Enter subject id to delete");
					id = in.nextLong();
					subjectService.deleteSubject(id);
					break;
				case "d":
					System.out.println("Enter book id to delete");
					id = in.nextLong();
					booKService.deleteBook(id);
					break;
				case "e":
					System.out.println("Enter book id to search");
					id = in.nextLong();
					booKService.searchBook(id);
					break;
				case "f":
					System.out.println("Enter subject id to search");
					id = in.nextLong();
					subjectService.searchSubject(id);
					break;	
				case "g":
					booKService.sortBookByTitle();
					break;
				case "h":
					subjectService.sortSubjectBySubjectTitle();
					break;
				case "i":
					booKService.sortBookByPublishDate();
					break;
				case "j":
					System.out.println("Thank you");
					break;
				default:
					System.out.println("Invalid Option.");
				}
			}while (!"j".equalsIgnoreCase(choice));
		}

		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (in != null) {
				in.close();
			}
		}
	}

	private static Book createBook() {
		Book book = new Book();
		System.out.println("Adding a Book");
		System.out.println("Please enter Book Id");
		book.setBookId(in.nextLong());
		System.out.println("Please enter Price");
		book.setPrice(in.nextDouble());
		System.out.println("Please enter Title");
		book.setTitle(in.next());
		System.out.println("Please enter Volume");
		book.setVolume(in.nextInt());
		book.setPublishDate(LocalDate.now());
		return book;
	}

	private static Subject createSubject(List<Book> books) {
		String choice = null;
		Subject subject = new Subject();
		System.out.println("Adding a Subject");
		System.out.println("Please enter Subject Id");
		subject.setSubjectId(in.nextLong());
		System.out.println("Please enter Subtitle");
		subject.setSubtitle(in.next());
		System.out.println("Please enter Duration In Hours");
		subject.setDurationInHours(in.nextInt());
		Set<Book> references = new HashSet<>();
		Map<Long, Book> bookMap = new HashMap<>();
		if(books != null && !books.isEmpty()) {
			System.out.println("All available book details");
			System.out.format("\n%10s %15s %15s %15s %15s", "Id", "Price", "Title", "Volume", "Publish Date");
			for(Book book : books) {
				System.out.format("\n%10d %15.2f %15s %15d %15s \n", book.getBookId(), book.getPrice(), book.getTitle(),
						book.getVolume(), book.getPublishDate());
				bookMap.put(book.getBookId(), book);
			}
			do {
				System.out.println("Please enter book id to enter as a reference");
				long id = in.nextLong();
				Book book = bookMap.get(id);
				if(book != null) {
					references.add(book);
				}
				else {
					System.out.println("Selected Book does not exists");
				}
				System.out.println("Do you want to add one more book as a reference(Y/N)");
				choice = in.next();
			}while (!"N".equalsIgnoreCase(choice));
			subject.setReferences(references);
		}
		else {
			System.out.println("No books exists to add references");
		}
		return subject;
	}

}
