package bookstore.controller.rest;

import bookstore.dto.BookRequestDto;
import bookstore.dto.BookResponseDto;
import bookstore.entity.Author;
import bookstore.entity.Book;
import bookstore.service.AuthorService;
import bookstore.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        List<BookResponseDto> responseDtos = bookService.getAllBooks().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(convertToResponseDto(book));
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody BookRequestDto requestDto) {
        Book book = new Book();
        book.setTitle(requestDto.getTitle());
        book.setPrice(requestDto.getPrice());
        book.setStockQuantity(requestDto.getStockQuantity());
        book.setPublishedYear(requestDto.getPublishedYear());

        Book savedBook = bookService.createBook(book, requestDto.getAuthorId());
        return new ResponseEntity<>(convertToResponseDto(savedBook), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDto requestDto) {
        Book bookDetails = new Book();
        bookDetails.setTitle(requestDto.getTitle());
        bookDetails.setPrice(requestDto.getPrice());
        bookDetails.setStockQuantity(requestDto.getStockQuantity());
        bookDetails.setPublishedYear(requestDto.getPublishedYear());

        Book updatedBook = bookService.updateBook(id, bookDetails, requestDto.getAuthorId());
        return ResponseEntity.ok(convertToResponseDto(updatedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    private BookResponseDto convertToResponseDto(Book book) {
        BookResponseDto dto = new BookResponseDto();
        dto.setBookId(book.getBookId());
        dto.setTitle(book.getTitle());
        dto.setPrice(book.getPrice());
        dto.setStockQuantity(book.getStockQuantity());
        dto.setPublishedYear(book.getPublishedYear());

        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getAuthorId());
            dto.setAuthorName(book.getAuthor().getAuthorName());
        }

        return dto;
    }
}