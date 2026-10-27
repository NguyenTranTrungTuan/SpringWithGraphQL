package bookstore.controller.graphql;

import bookstore.dto.AuthorRequestDto;
import bookstore.entity.Author;
import bookstore.service.AuthorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorGraphQLController {

    private final AuthorService authorService;

    public AuthorGraphQLController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @QueryMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @QueryMapping
    public Author getAuthorById(@Argument Long id) {
        return authorService.getAuthorById(id);
    }

    @MutationMapping
    public Author createAuthor(@Argument AuthorRequestDto input) {
        Author author = new Author();
        author.setAuthorName(input.getAuthorName());
        author.setBio(input.getBio());
        return authorService.createAuthor(author);
    }

    @MutationMapping
    public Author updateAuthor(@Argument Long id, @Argument AuthorRequestDto input) {
        Author authorDetails = new Author();
        authorDetails.setAuthorName(input.getAuthorName());
        authorDetails.setBio(input.getBio());
        return authorService.updateAuthor(id, authorDetails);
    }

    @MutationMapping
    public Boolean deleteAuthor(@Argument Long id) {
        authorService.deleteAuthor(id);
        return true;
    }
}