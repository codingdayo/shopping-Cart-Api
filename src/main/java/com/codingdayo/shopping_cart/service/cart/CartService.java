package com.codingdayo.shopping_cart.service.cart;

import com.codingdayo.shopping_cart.Exceptions.ResourceNotFoundException;
import com.codingdayo.shopping_cart.model.Cart;
import com.codingdayo.shopping_cart.model.User;
import com.codingdayo.shopping_cart.repository.CartItemRepository;
import com.codingdayo.shopping_cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
//to make it a spring bean
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    //creating cardId manually since the user isn't there.
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart Not Found"));
      BigDecimal totalAmount =  cart.getTotalAmount();
      cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        //cartItemRepository.deleteAllByCartId(id);
        //cart.getCartItems().clear();         /
        cartRepository.deleteById(id);

    }

    //or add the transactional annotation and it works.
    //@Transactional
    //@Override
    //public void clearCart(Long id) {
    //    Cart cart = getCart(id);
    //    cartItemRepository.deleteAllByCartId(id);
    //    cart.getCartItems().clear();         /
    //    cartRepository.deleteById(id);
    //
    //}


    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);


        return cart.getTotalAmount();
                //replacing this with above
                //cart.getCartItems()
                //.stream()
                //.map(CartItem:: getTotalPrice)
                //.reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    @Override
    public Cart initializeNewCart(User user){
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }


    //public Long initalizeNewCart() {
    //    Cart newCart = new Cart();
    //    Long newCartId = cartIdGenerator.incrementAndGet();
    //    newCart.setId(newCartId);
    //    return cartRepository.save(newCart).getId();
    //
    //}

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
