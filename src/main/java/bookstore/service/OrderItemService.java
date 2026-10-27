package bookstore.service;

import bookstore.entity.OrderItem;
import java.util.List;

public interface OrderItemService {
    List<OrderItem> getItemsByOrderId(Long orderId);
    OrderItem createOrderItem(Long orderId, Long bookId, Integer quantity);
    OrderItem updateOrderItem(Long orderItemId, Integer newQuantity);
    void deleteOrderItem(Long itemId);
}