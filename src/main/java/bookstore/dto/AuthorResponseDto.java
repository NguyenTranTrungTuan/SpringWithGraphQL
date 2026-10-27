package bookstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorResponseDto {
    private Long authorId;
    private String authorName;
    private String bio;
}