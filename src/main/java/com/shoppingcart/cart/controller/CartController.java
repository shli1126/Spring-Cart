package com.shoppingcart.cart.controller;


import com.shoppingcart.cart.exception.ResourceNotFoundException;
import com.shoppingcart.cart.model.Cart;
import com.shoppingcart.cart.response.ApiResponse;
import com.shoppingcart.cart.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

  @GetMapping("/{cartId}/my-cart")
  public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
    try {
      Cart cart = cartService.getCart(cartId);
      return ResponseEntity.ok(new ApiResponse("success", cart));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @DeleteMapping("/{cartId}/clear")
  public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
    try {
      cartService.clearCart(cartId);
      return ResponseEntity.ok(new ApiResponse("success", null));
    }
    catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("/{cartId}/cart/total-price")
  public ResponseEntity<ApiResponse> getTotalPrice(@PathVariable Long cartId) {
    try {
      BigDecimal amount = cartService.getTotalPrice(cartId);
      return ResponseEntity.ok(new ApiResponse("success", amount));
    } catch(ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

}
