package bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequestDto {

    @NotBlank(message = "Tên sách không được để trống")
    private String title;

    @NotNull(message = "Giá sách không được để trống")
    @Min(value = 0, message = "Giá sách phải lớn hơn hoặc bằng 0")
    private Double price;

    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho không được âm")
    private Integer stockQuantity;

    private Integer publishedYear;

    @NotNull(message = "ID tác giả không được để trống")
    private Long authorId;
}