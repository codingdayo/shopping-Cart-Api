package com.codingdayo.shopping_cart.Controller;

import com.codingdayo.shopping_cart.Exceptions.ResourceNotFoundException;
import com.codingdayo.shopping_cart.dto.OrderDto;
import com.codingdayo.shopping_cart.model.Order;
import com.codingdayo.shopping_cart.response.ApiResponse;
import com.codingdayo.shopping_cart.service.Order.IOrderService;
import com.codingdayo.shopping_cart.service.Order.OrderService;
import com.codingdayo.shopping_cart.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try{
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Item order successful ", orderDto));
        } catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/getOrder")
    public  ResponseEntity<ApiResponse> getOrderBy(@PathVariable Long orderId){
        try{
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Found", order));
        } catch (ResourceNotFoundException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops", e.getMessage()));
        }
       }

    @GetMapping("/{userId}/getUserOrder")
    public  ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
        try{
           List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Found", order));
        } catch (ResourceNotFoundException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops", e.getMessage()));
        }
    }

}
