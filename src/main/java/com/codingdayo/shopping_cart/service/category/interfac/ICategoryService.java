package com.codingdayo.shopping_cart.service.category.interfac;

import com.codingdayo.shopping_cart.model.Category;

import java.util.List;

public interface ICategoryService {

    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    List<Category> getAllCategories();

    Category addCategory(Category category);

    Category updateCategory(Category category, Long id);

    void deleteCategoryById(Long id);


}
