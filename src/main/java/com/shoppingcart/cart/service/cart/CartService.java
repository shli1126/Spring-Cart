package com.shoppingcart.cart.service.cart;

import com.shoppingcart.cart.exception.ResourceNotFoundException;
import com.shoppingcart.cart.model.Cart;
import com.shoppingcart.cart.model.CartItem;
import com.shoppingcart.cart.repository.CartItemRepository;
import com.shoppingcart.cart.repository.CartRepository;
import java.math.BigDecimal;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  @Override
  public Cart getCart(Long id) {
    return cartRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
  }

  @Override
  public void clearCart(Long id) {
    Cart cart = getCart(id);
    cartItemRepository.deleteAllByCartId(id);
    cart.getItems().clear();
    cartRepository.deleteById(id);
  }

  @Override
  public BigDecimal getTotalPrice(Long id) {
    Cart cart = getCart(id);
    //stream reduce, first initial value and then the method used to reduced to a single value
    return cart.getItems().stream().map(CartItem :: getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
