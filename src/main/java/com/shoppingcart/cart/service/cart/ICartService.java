package com.shoppingcart.cart.service.cart;

import com.shoppingcart.cart.model.Cart;
import java.math.BigDecimal;

public interface ICartService {
  Cart getCart(Long id);

  void clearCart(Long id);

  BigDecimal getTotalPrice(Long id);
}
