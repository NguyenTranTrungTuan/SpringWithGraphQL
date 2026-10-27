package bookstore.service.impl;

import bookstore.entity.Author;
import bookstore.repository.AuthorRepository;
import bookstore.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác giả với ID: " + id));
    }

    @Override
    @Transactional
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public Author updateAuthor(Long id, Author authorDetails) {
        Author existingAuthor = getAuthorById(id);

        existingAuthor.setAuthorName(authorDetails.getAuthorName());
        existingAuthor.setBio(authorDetails.getBio());

        return authorRepository.save(existingAuthor);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        Author author = getAuthorById(id);
        authorRepository.delete(author);
    }
}