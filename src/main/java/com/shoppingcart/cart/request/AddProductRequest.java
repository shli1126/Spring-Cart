package com.shoppingcart.cart.request;

import com.shoppingcart.cart.model.Category;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int  inventory;
    private Category category;
}
