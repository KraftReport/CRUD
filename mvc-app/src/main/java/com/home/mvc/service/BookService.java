package com.home.mvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.home.mvc.model.Book;

@Service
public class BookService {

	private static List<Book> bookList;

	static {
		bookList = new ArrayList<>();
		bookList.add(new Book("to kill a mocking bird", 1968));
		bookList.add(new Book("the great gastby", 1925));
		bookList.add(new Book("1984", 1949));
		bookList.add(new Book("the catcher in the rye", 1951));
	}

	public List<Book> getAllBookByLogin(String login) {
		if (login != null) {
			return bookList;
		}

		return bookList.stream().filter(b -> b.getYear() > 1951).toList();
	}

    public void add(Book book) {
		bookList.add(book);
    }

	public Book findBookByTitle(String title) {
		var bl = bookList.stream().filter(b->b.getTitle().equals(title)).findFirst().orElseThrow(IllegalArgumentException::new);
		bookList.remove(bl);
		return  bl;
	}

	public void edit(Book book) {
		bookList.add(book);
	}

	public void delete(Book book) {
		bookList.remove(book);
	}
}
