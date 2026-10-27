package bookstore.service.impl;

import bookstore.entity.Author;
import bookstore.entity.Book;
import bookstore.repository.AuthorRepository;
import bookstore.repository.BookRepository;
import bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với ID: " + id));
    }

    @Override
    @Transactional
    public Book createBook(Book book, Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả với ID: " + authorId));

        book.setAuthor(author);
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Long id, Book bookDetails, Long authorId) {
        Book existingBook = getBookById(id);

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả với ID: " + authorId));

        existingBook.setTitle(bookDetails.getTitle());
        existingBook.setPrice(bookDetails.getPrice());
        existingBook.setStockQuantity(bookDetails.getStockQuantity());
        existingBook.setPublishedYear(bookDetails.getPublishedYear());
        existingBook.setAuthor(author);

        return bookRepository.save(existingBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
}