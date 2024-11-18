package com.shoppingcart.cart.repository;

import com.shoppingcart.cart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {}
