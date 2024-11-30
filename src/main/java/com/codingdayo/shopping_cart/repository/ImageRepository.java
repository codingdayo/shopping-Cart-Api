package com.codingdayo.shopping_cart.repository;

import com.codingdayo.shopping_cart.model.Image;
import com.codingdayo.shopping_cart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProductId(Long id);
}
