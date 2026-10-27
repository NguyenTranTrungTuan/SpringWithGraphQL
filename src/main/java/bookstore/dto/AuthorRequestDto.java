package bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorRequestDto {

    @NotBlank(message = "Tên tác giả không được để trống")
    private String authorName;

    private String bio;
}