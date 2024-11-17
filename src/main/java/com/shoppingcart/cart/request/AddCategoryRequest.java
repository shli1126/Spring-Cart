package com.shoppingcart.cart.request;

import com.shoppingcart.cart.model.Product;

import lombok.Data;

import java.util.List;

@Data
public class AddCategoryRequest {

    private Long id;
    private String name;
    private List<Product> products;

}
