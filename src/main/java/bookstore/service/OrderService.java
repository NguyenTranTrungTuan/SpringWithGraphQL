package bookstore.service;

import bookstore.entity.Order;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order createOrder(Long customerId, Order orderDetails);
    void deleteOrder(Long id);
}