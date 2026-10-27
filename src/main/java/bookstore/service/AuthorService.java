package bookstore.service;

import bookstore.entity.Author;
import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();
    Author getAuthorById(Long id);
    Author createAuthor(Author author);
    Author updateAuthor(Long id, Author authorDetails);
    void deleteAuthor(Long id);
}