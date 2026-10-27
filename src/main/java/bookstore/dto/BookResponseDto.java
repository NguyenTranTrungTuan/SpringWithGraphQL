package bookstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponseDto {
    private Long bookId;
    private String title;
    private Double price;
    private Integer stockQuantity;
    private Integer publishedYear;
    private Long authorId;
    private String authorName;
}