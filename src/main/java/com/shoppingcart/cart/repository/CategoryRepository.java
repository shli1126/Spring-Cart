package com.shoppingcart.cart.repository;

import com.shoppingcart.cart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  Category findByName(String name);
  boolean existByName(String name);
}