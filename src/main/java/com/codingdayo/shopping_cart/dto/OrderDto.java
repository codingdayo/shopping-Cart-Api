package com.codingdayo.shopping_cart.dto;

import com.codingdayo.shopping_cart.model.OrderItem;
import com.codingdayo.shopping_cart.model.OrderStatus;
import com.codingdayo.shopping_cart.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class OrderDto {

    private Long orderId;
    private Long userId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDto> orderItems;
}
