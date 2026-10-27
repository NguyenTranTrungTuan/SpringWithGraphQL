package bookstore.controller.graphql;

import bookstore.dto.OrderItemRequestDto;
import bookstore.dto.OrderItemUpdateRequestDto;
import bookstore.entity.OrderItem;
import bookstore.service.OrderItemService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrderItemGraphQLController {

    private final OrderItemService orderItemService;

    public OrderItemGraphQLController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @QueryMapping
    public List<OrderItem> getItemsByOrderId(@Argument Long orderId) {
        return orderItemService.getItemsByOrderId(orderId);
    }

    @MutationMapping
    public OrderItem createOrderItem(
            @Argument Long orderId,
            @Argument OrderItemRequestDto input
    ) {
        return orderItemService.createOrderItem(
                orderId,
                input.getBookId(),
                input.getQuantity()
        );
    }

    @MutationMapping
    public OrderItem updateOrderItem(
            @Argument Long orderItemId,
            @Argument OrderItemUpdateRequestDto input
    ) {
        return orderItemService.updateOrderItem(orderItemId, input.getNewQuantity());
    }

    @MutationMapping
    public Boolean deleteOrderItem(@Argument Long itemId) {
        orderItemService.deleteOrderItem(itemId);
        return true;
    }
}