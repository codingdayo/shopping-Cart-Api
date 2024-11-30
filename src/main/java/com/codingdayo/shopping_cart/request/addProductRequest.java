package com.codingdayo.shopping_cart.request;

import com.codingdayo.shopping_cart.model.Category;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class addProductRequest {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;

}
