package com.shoppingcart.cart.repository;

import com.shoppingcart.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {}
