package com.codingdayo.shopping_cart.service.cart;

import com.codingdayo.shopping_cart.model.Cart;
import com.codingdayo.shopping_cart.model.User;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart (Long id);
    BigDecimal getTotalPrice(Long id);

    //Long initalizeNewCart();

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
