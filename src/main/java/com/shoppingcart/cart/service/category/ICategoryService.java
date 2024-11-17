package com.shoppingcart.cart.service.category;

import com.shoppingcart.cart.model.Category;
import com.shoppingcart.cart.request.AddCategoryRequest;
import com.shoppingcart.cart.request.UpdateCategoryRequest;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long categoryId);
    void deleteCategoryById(Long id);
}
