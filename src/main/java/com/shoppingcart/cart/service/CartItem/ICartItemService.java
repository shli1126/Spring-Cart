package com.shoppingcart.cart.service.CartItem;

public interface ICartItemService {

  void addItemToCart(Long cartId, Long productId, int quantity);

  void removeItemFromCart(Long cartId, Long productId);

  void updateItemQuantity(Long cartId, Long productId, int quantity);
}
