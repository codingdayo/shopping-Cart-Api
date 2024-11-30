package com.codingdayo.shopping_cart.dto;

import lombok.Data;

@Data
public class ImageDto {

    private Long id;
    private String fileName;
    private String downloadUrl;

//    using this to solve the byteresource error you were getting when an image
//    was attached to a product.
}
