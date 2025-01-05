package com.shoppingcart.cart.service.order;

import com.shoppingcart.cart.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long orderId);
    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}
