package bookstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerResponseDto {
    private Long customerId;
    private String fullName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
}