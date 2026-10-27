package bookstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private Long customerId;
    private String customerName;
    private String customerEmail;
}