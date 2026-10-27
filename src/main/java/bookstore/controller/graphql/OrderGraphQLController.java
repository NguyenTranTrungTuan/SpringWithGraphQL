package bookstore.controller.graphql;

import bookstore.dto.OrderRequestDto;
import bookstore.entity.Order;
import bookstore.service.OrderService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrderGraphQLController {

    private final OrderService orderService;

    public OrderGraphQLController(OrderService orderService) {
        this.orderService = orderService;
    }

    @QueryMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @QueryMapping
    public Order getOrderById(@Argument Long id) {
        return orderService.getOrderById(id);
    }

    @MutationMapping
    public Order createOrder(@Argument OrderRequestDto input) {
        Order orderDetails = new Order();

        return orderService.createOrder(input.getCustomerId(), orderDetails);
    }

    @MutationMapping
    public Boolean deleteOrder(@Argument Long id) {
        orderService.deleteOrder(id);
        return true;
    }
}