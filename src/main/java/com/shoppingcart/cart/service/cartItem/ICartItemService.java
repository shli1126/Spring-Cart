package com.shoppingcart.cart.service.cartItem;

public interface ICartItemService {

  void addItemToCart(Long cartId, Long productId, int quantity);

  void removeItemFromCart(Long cartId, Long productId);

  void updateItemQuantity(Long cartId, Long productId, int quantity);
}
