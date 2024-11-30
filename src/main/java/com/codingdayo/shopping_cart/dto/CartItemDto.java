package com.codingdayo.shopping_cart.dto;

import com.codingdayo.shopping_cart.model.Cart;
import com.codingdayo.shopping_cart.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {

    private Long id;
    private Integer quantity;
    private BigDecimal unitPrice;
    private Product product;
}
