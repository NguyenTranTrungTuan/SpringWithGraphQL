package bookstore.service.impl;

import bookstore.entity.Book;
import bookstore.entity.Order;
import bookstore.entity.OrderItem;
import bookstore.repository.BookRepository;
import bookstore.repository.OrderItemRepository;
import bookstore.repository.OrderRepository;
import bookstore.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

    @Override
    public List<OrderItem> getItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrder_OrderId(orderId);
    }

    @Override
    @Transactional
    public OrderItem createOrderItem(Long orderId, Long bookId, Integer quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với ID: " + bookId));

        if (book.getStockQuantity() < quantity) {
            throw new RuntimeException("Sách '" + book.getTitle() + "' không đủ số lượng trong kho. Còn lại: " + book.getStockQuantity());
        }

        book.setStockQuantity(book.getStockQuantity() - quantity);
        bookRepository.save(book);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(book);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(book.getPrice());

        OrderItem savedItem = orderItemRepository.save(orderItem);

        order.setTotalAmount(order.getTotalAmount() + (book.getPrice() * quantity));
        orderRepository.save(order);

        return savedItem;
    }

    @Override
    @Transactional
    public OrderItem updateOrderItem(Long orderItemId, Integer newQuantity) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết đơn hàng với ID: " + orderItemId));

        Order order = orderItem.getOrder();
        Book book = orderItem.getBook();

        int oldQuantity = orderItem.getQuantity();
        int quantityDifference = newQuantity - oldQuantity;

        if (quantityDifference > 0 && book.getStockQuantity() < quantityDifference) {
            throw new RuntimeException("Sách '" + book.getTitle() + "' không đủ số lượng trong kho. Còn thiếu: " + (quantityDifference - book.getStockQuantity()));
        }

        book.setStockQuantity(book.getStockQuantity() - quantityDifference);
        bookRepository.save(book);

        orderItem.setQuantity(newQuantity);
        OrderItem savedItem = orderItemRepository.save(orderItem);

        order.setTotalAmount(order.getTotalAmount() + (orderItem.getPrice() * quantityDifference));
        orderRepository.save(order);

        return savedItem;
    }

    @Override
    @Transactional
    public void deleteOrderItem(Long itemId) {
        OrderItem orderItem = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết đơn hàng với ID: " + itemId));

        Order order = orderItem.getOrder();
        Book book = orderItem.getBook();

        book.setStockQuantity(book.getStockQuantity() + orderItem.getQuantity());
        bookRepository.save(book);

        double itemTotal = orderItem.getPrice() * orderItem.getQuantity();
        order.setTotalAmount(Math.max(0.0, order.getTotalAmount() - itemTotal));
        orderRepository.save(order);

        orderItemRepository.delete(orderItem);
    }
}