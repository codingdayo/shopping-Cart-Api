package com.codingdayo.shopping_cart.Controller;

import com.codingdayo.shopping_cart.Exceptions.ResourceNotFoundException;
import com.codingdayo.shopping_cart.model.Cart;
import com.codingdayo.shopping_cart.model.User;
import com.codingdayo.shopping_cart.response.ApiResponse;
import com.codingdayo.shopping_cart.service.cart.CartService;
import com.codingdayo.shopping_cart.service.cart.ICartItemService;
import com.codingdayo.shopping_cart.service.cart.ICartService;
import com.codingdayo.shopping_cart.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final CartService cartService;
    private final IUserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity){
     try   {

         User user = userService.getUserById(1L);
         Cart cart =  cartService.initializeNewCart(user);

            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Item added to cart", null));
        }catch (ResourceNotFoundException e){
         return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
     }
    }

    @DeleteMapping("/{cartId}/item/{productId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId){
        cartItemService.removeItemFromCart(cartId, productId);
        return ResponseEntity.ok(new ApiResponse("Item removed from cart", null));
    }

    @PutMapping("/{cartId}/item/{productId}/update")
    public  ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable
                                                           Long cartId,
                                                           @PathVariable Long productId,
                                                           @RequestParam Integer quantity){


      try  {
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Item quantity update", null));
            } catch (ResourceNotFoundException e){
          return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
      }
    }
}
