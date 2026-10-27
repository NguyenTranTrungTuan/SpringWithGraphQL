package bookstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {
    private Long itemId;
    private Integer quantity;
    private Double price;
    private Double subTotal;
    private Long bookId;
    private String bookTitle;
}