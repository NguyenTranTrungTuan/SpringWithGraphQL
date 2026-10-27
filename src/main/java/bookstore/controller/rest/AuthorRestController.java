package bookstore.controller.rest;

import bookstore.dto.AuthorRequestDto;
import bookstore.dto.AuthorResponseDto;
import bookstore.entity.Author;
import bookstore.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> getAllAuthors() {
        List<AuthorResponseDto> responseDtos = authorService.getAllAuthors().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthorById(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        return ResponseEntity.ok(convertToResponseDto(author));
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@Valid @RequestBody AuthorRequestDto requestDto) {
        Author author = new Author();
        author.setAuthorName(requestDto.getAuthorName());
        author.setBio(requestDto.getBio());

        Author savedAuthor = authorService.createAuthor(author);

        return new ResponseEntity<>(convertToResponseDto(savedAuthor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequestDto requestDto) {
        Author authorDetails = new Author();
        authorDetails.setAuthorName(requestDto.getAuthorName());
        authorDetails.setBio(requestDto.getBio());

        Author updatedAuthor = authorService.updateAuthor(id, authorDetails);
        return ResponseEntity.ok(convertToResponseDto(updatedAuthor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    private AuthorResponseDto convertToResponseDto(Author author) {
        AuthorResponseDto dto = new AuthorResponseDto();
        dto.setAuthorId(author.getAuthorId());
        dto.setAuthorName(author.getAuthorName());
        dto.setBio(author.getBio());
        return dto;
    }
}