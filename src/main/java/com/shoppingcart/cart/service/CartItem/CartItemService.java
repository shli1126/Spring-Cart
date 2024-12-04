package com.shoppingcart.cart.service.CartItem;


import com.shoppingcart.cart.exception.ResourceNotFoundException;
import com.shoppingcart.cart.model.Cart;
import com.shoppingcart.cart.model.CartItem;
import com.shoppingcart.cart.model.Product;
import com.shoppingcart.cart.repository.CartItemRepository;
import com.shoppingcart.cart.repository.CartRepository;
import com.shoppingcart.cart.service.cart.ICartService;
import com.shoppingcart.cart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {

        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        // check if item in cart
        CartItem cartItem = cart.getItems().stream().filter(item -> item.getProduct()
                .getId()
                .equals(product.getId()))
                .findFirst().orElse(new CartItem());
        // if item not in cart
        if (cartItem.getId() == null) {
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            // if item in cart, update quantity
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = getCartItem(cartId, productId);
        // if item not in cart
        cart.removeItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart  = cartService.getCart(cartId);
        CartItem cartItem = getCartItem(cartId, productId);
        // if item not in cart
        cartItem.setUnitPrice(cartItem.getUnitPrice());
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();

        BigDecimal totalAmount = cart.getItems().stream().map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems().stream().filter(item -> item.getProduct()
                .getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found"));
    }
}
