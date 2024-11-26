package com.shoppingcart.cart.repository;

import com.shoppingcart.cart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long ProductId);
}
