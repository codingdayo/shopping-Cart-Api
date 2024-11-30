package com.codingdayo.shopping_cart.dto;

import com.codingdayo.shopping_cart.model.Category;
import com.codingdayo.shopping_cart.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;

    private Category category;

    //The error you got when trying to return
    // products with images, you solve it using ImageDto instead in the list.
    private List<ImageDto> images;
}
