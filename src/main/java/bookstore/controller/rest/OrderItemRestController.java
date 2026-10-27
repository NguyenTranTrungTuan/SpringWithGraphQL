package bookstore.controller.rest;

import bookstore.dto.OrderItemRequestDto;
import bookstore.dto.OrderItemResponseDto;
import bookstore.dto.OrderItemUpdateRequestDto;
import bookstore.entity.OrderItem;
import bookstore.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders/{orderId}/items")
@RequiredArgsConstructor
public class OrderItemRestController {

    private final OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<List<OrderItemResponseDto>> getItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItemResponseDto> responseDtos = orderItemService.getItemsByOrderId(orderId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping
    public ResponseEntity<OrderItemResponseDto> createOrderItem(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderItemRequestDto requestDto) {

        OrderItem savedItem = orderItemService.createOrderItem(
                orderId,
                requestDto.getBookId(),
                requestDto.getQuantity()
        );

        return new ResponseEntity<>(convertToResponseDto(savedItem), HttpStatus.CREATED);
    }

    @PutMapping("/{orderItemId}")
    public ResponseEntity<OrderItemResponseDto> updateOrderItem(
            @PathVariable Long orderItemId,
            @Valid @RequestBody OrderItemUpdateRequestDto requestDto) {

        OrderItem updatedItem = orderItemService.updateOrderItem(
                orderItemId,
                requestDto.getNewQuantity()
        );

        return new ResponseEntity<>(convertToResponseDto(updatedItem), HttpStatus.OK);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long itemId) {
        orderItemService.deleteOrderItem(itemId);
        return ResponseEntity.noContent().build();
    }

    private OrderItemResponseDto convertToResponseDto(OrderItem orderItem) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setItemId(orderItem.getItemId());
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPrice());

        if (orderItem.getQuantity() != null && orderItem.getPrice() != null) {
            dto.setSubTotal(orderItem.getQuantity() * orderItem.getPrice());
        } else {
            dto.setSubTotal(0.0);
        }

        if (orderItem.getBook() != null) {
            dto.setBookId(orderItem.getBook().getBookId());
            dto.setBookTitle(orderItem.getBook().getTitle());
        }

        return dto;
    }
}