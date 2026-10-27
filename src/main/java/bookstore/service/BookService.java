package bookstore.service;

import bookstore.entity.Book;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book createBook(Book book, Long authorId);
    Book updateBook(Long id, Book bookDetails, Long authorId);
    void deleteBook(Long id);
}