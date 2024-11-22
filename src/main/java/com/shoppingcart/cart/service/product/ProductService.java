package com.shoppingcart.cart.service.product;

import com.shoppingcart.cart.exception.ProductNotFoundException;
import com.shoppingcart.cart.exception.ResourceNotFoundException;
import com.shoppingcart.cart.model.Category;
import com.shoppingcart.cart.model.Product;
import com.shoppingcart.cart.repository.CategoryRepository;
import com.shoppingcart.cart.repository.ProductRepository;
import com.shoppingcart.cart.request.AddProductRequest;
import com.shoppingcart.cart.request.UpdateProductRequest;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // DTO AddProductRequest
        //if category exist, using the exist category, else, creat a new category
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found!"));
    }

    @Override
    public void deleteProductById(Long id) {
    productRepository
        .findById(id)
        .ifPresentOrElse(
            productRepository::delete,
            () -> {
              throw new ResourceNotFoundException("Product Not Found!");
            });
    }

    @Override
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        //first check if the product exist in the repository
        // if existed, update it using the request and save
        // if not existed, throw exception
        return productRepository.findById(productId)
                .map(product -> updateExistingProduct(product, request))
                .map(productRepository :: save)
                .orElseThrow(() -> new ProductNotFoundException("Product not Found"));
    }

    private Product updateExistingProduct(Product product, UpdateProductRequest request) {
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setInventory(request.getInventory());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        product.setCategory(category);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);

    }
}
