package com.shoppingcart.cart.dto;

import com.shoppingcart.cart.model.Category;
import com.shoppingcart.cart.model.Image;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {

    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int  inventory;
    private Category category;
    private List<ImageDto> images;

}
