package bookstore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {

    @NotNull(message = "ID khách hàng không được để trống")
    private Long customerId;
}