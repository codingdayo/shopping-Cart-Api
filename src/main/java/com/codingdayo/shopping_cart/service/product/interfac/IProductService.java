package com.codingdayo.shopping_cart.service.product.interfac;

import com.codingdayo.shopping_cart.dto.ProductDto;
import com.codingdayo.shopping_cart.model.Product;
import com.codingdayo.shopping_cart.request.ProductUpdateRequest;
import com.codingdayo.shopping_cart.request.addProductRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(addProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String Category, String brand);
    //E.g Apple + phones
    List<Product> getProductsByBrandAndName(String Brand, String name);
    List<Product> getProductsByName(String name);
    Long countProductsByBrandAndName(String brand, String name);

    //converting the product with model mapper
    ProductDto convertToDto(Product product);

    //then the utility class to convert the
    List<ProductDto> getConvertedProducts(List<Product> products);
}
