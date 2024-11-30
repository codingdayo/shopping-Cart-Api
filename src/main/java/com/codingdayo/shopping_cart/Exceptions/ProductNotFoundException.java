package com.codingdayo.shopping_cart.Exceptions;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message){

        super(message);
    }
}
