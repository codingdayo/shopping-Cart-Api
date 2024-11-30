package com.codingdayo.shopping_cart.service.cart;

import com.codingdayo.shopping_cart.Exceptions.ResourceNotFoundException;
import com.codingdayo.shopping_cart.model.Cart;
import com.codingdayo.shopping_cart.model.CartItem;
import com.codingdayo.shopping_cart.model.Product;
import com.codingdayo.shopping_cart.repository.CartItemRepository;
import com.codingdayo.shopping_cart.repository.CartRepository;
import com.codingdayo.shopping_cart.service.product.interfac.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final CartService cartService;
    private final  CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
    //    1. Get the cart
    //    2. get the product
    //    3. check if the product/item already in the cart,
    //    if yes, increase the quantity with the requested quantity
    //    if no, then initiate a new CartItem entry.
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems()
                .stream()
                //comparing the product using the id. (.getId()) with the producyId
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() == null){
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            //if the item exists already then you just add it to the existing quantity
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
    //    Note that cartItem is a product.

        CartItem itemToRemove = getCartItem(cartId, productId);
    //    CREATED A PUBLIC CLASS FOR THE IMPLEMENTATION OF THIS BELOW
    //    CartItem itemToRemove = cart.getCartItems()
    //            .stream()
    //            .filter(item -> item.getProduct().getId().equals(productId))
    //            .findFirst().orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        //Calling this method "removeItem" from the Cart class
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().ifPresent(item -> {
                    int newQuantity = item.getQuantity() + quantity;
                    item.setQuantity(newQuantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });

        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart = cartService.getCart(cartId);
        return  cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    }


}


