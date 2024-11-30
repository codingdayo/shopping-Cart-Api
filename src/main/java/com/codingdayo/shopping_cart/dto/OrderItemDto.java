package com.codingdayo.shopping_cart.dto;

import com.codingdayo.shopping_cart.model.Order;
import com.codingdayo.shopping_cart.model.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long productId;
    private String productName;
    private String productBrand;
    private int quantity;
    private BigDecimal price;


}
