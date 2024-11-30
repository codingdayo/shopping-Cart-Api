package com.codingdayo.shopping_cart.Exceptions;

public class ProductAlreadyExistException extends RuntimeException{

    public ProductAlreadyExistException(String message){
        super(message);
    }

}
