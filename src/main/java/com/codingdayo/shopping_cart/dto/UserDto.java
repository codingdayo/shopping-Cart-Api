package com.codingdayo.shopping_cart.dto;

import com.codingdayo.shopping_cart.model.Cart;
import com.codingdayo.shopping_cart.model.Order;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import java.util.List;

//need to return the basic information of the User
@Data
public class UserDto {


    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<OrderDto> orders;
    //Not returning the raw cart to the user because it will string along
    // many other details not needed. Hence, a cartdto is created
    private CartDto cart;


}

