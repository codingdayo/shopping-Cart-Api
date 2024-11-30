package com.codingdayo.shopping_cart.service.Order;

import com.codingdayo.shopping_cart.dto.OrderDto;
import com.codingdayo.shopping_cart.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder (Long userId);
    OrderDto getOrder(Long orderId);


    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
