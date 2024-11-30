package com.codingdayo.shopping_cart.dto;

import com.codingdayo.shopping_cart.model.CartItem;
import com.codingdayo.shopping_cart.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class CartDto {

    private Long id;
    private BigDecimal totalAmount;
    private Set<CartItem> cartItems;
}
