package com.codingdayo.shopping_cart.request;

import com.codingdayo.shopping_cart.model.Product;
import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String Email;
    private String passWord;
}
