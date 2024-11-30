package com.codingdayo.shopping_cart.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;


    private BigDecimal price;
    private int inventory;
    private String description;

    //category and products are independent of each other
    //Many products to one category
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id ")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
    //orphanRemoval = true; when a product is being deleted, all the images associated with it too will be gone as well
    //One product to many images


    public Product(String name, String brand, BigDecimal price,
                   int inventory, String description,
                   Category category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
    }

}
