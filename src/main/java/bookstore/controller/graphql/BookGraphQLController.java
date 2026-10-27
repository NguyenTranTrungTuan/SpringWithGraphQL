package bookstore.controller.graphql;

import bookstore.dto.BookRequestDto;
import bookstore.entity.Book;
import bookstore.service.BookService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookGraphQLController {

    private final BookService bookService;

    public BookGraphQLController(BookService bookService) {
        this.bookService = bookService;
    }

    @QueryMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Book getBookById(@Argument Long id) {
        return bookService.getBookById(id);
    }

    @MutationMapping
    public Book createBook(@Argument BookRequestDto input) {
        Book book = new Book();
        book.setTitle(input.getTitle());
        book.setPrice(input.getPrice());
        book.setStockQuantity(input.getStockQuantity());
        book.setPublishedYear(input.getPublishedYear());

        return bookService.createBook(book, input.getAuthorId());
    }

    @MutationMapping
    public Book updateBook(@Argument Long id, @Argument BookRequestDto input) {
        Book bookDetails = new Book();
        bookDetails.setTitle(input.getTitle());
        bookDetails.setPrice(input.getPrice());
        bookDetails.setStockQuantity(input.getStockQuantity());
        bookDetails.setPublishedYear(input.getPublishedYear());

        return bookService.updateBook(id, bookDetails, input.getAuthorId());
    }

    @MutationMapping
    public Boolean deleteBook(@Argument Long id) {
        bookService.deleteBook(id);
        return true;
    }
}