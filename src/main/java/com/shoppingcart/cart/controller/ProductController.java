package com.shoppingcart.cart.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.shoppingcart.cart.dto.ProductDto;
import com.shoppingcart.cart.exception.ResourceNotFoundException;
import com.shoppingcart.cart.model.Product;
import com.shoppingcart.cart.request.AddProductRequest;
import com.shoppingcart.cart.request.UpdateProductRequest;
import com.shoppingcart.cart.response.ApiResponse;
import com.shoppingcart.cart.service.product.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

  private final ProductService productService;

  @GetMapping("/all")
  public ResponseEntity<ApiResponse> getAllProducts() {
    List<Product> allProducts = productService.getAllProducts();
    List<ProductDto> convertedProducts = productService.getConvertedProducts(allProducts);
    return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
  }

  @GetMapping("/product/{productId}/product")
  public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
    try {
      Product product = productService.getProductById(productId);
      ProductDto productDto = productService.convertToDto(product);
      return ResponseEntity.ok(new ApiResponse("Success", productDto));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @PostMapping("/add")
  public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request) {
    try {
      Product product = productService.addProduct(request);
      ProductDto productDto = productService.convertToDto(product);

      return ResponseEntity.ok(new ApiResponse("Success", productDto));
    } catch (RuntimeException e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR)
          .body(new ApiResponse(e.getMessage(), null));
    }
  }

  @PutMapping("/product/{productId}/update")
  public ResponseEntity<ApiResponse> updateProduct(
      @PathVariable Long productId, @RequestBody UpdateProductRequest request) {
    try {
      Product product = productService.updateProduct(request, productId);
      ProductDto productDto = productService.convertToDto(product);

      return ResponseEntity.ok(new ApiResponse("Success", productDto));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @DeleteMapping("/product/{productId}/delete")
  public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
    try {
      productService.deleteProductById(productId);
      return ResponseEntity.ok(new ApiResponse("Success", null));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("/products/by/brand-and-name")
  public ResponseEntity<ApiResponse> getProductByBrandAndName(
      @RequestParam String brandName, @RequestParam String productName) {

    try {
      List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
      List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

      if (products.isEmpty()) {
        return ResponseEntity.status(NOT_FOUND)
            .body(new ApiResponse("No product found with " + brandName + " " + productName, null));
      }
      return ResponseEntity.ok(new ApiResponse("Success", convertedProducts));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR)
          .body(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("/products/by/category-and-brand")
  public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(
      @RequestParam String category, @RequestParam String brand) {
    try {
      List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
      List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

      if (products.isEmpty()) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
      }
      return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR)
          .body(new ApiResponse("error", e.getMessage()));
    }
  }

  @GetMapping("/products/{name}/products")
  public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
    try {
      List<Product> products = productService.getProductsByName(name);
      List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

      if (products.isEmpty()) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
      }
      return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR)
          .body(new ApiResponse("error", e.getMessage()));
    }
  }

  @GetMapping("/product/by-brand")
  public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand) {
    try {
      List<Product> products = productService.getProductsByBrand(brand);
      List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

      if (products.isEmpty()) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
      }
      return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
    } catch (Exception e) {
      return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("/product/{category}/all/products")
  public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
    try {
      List<Product> products = productService.getProductsByCategory(category);
      List<ProductDto> convertedProducts = productService.getConvertedProducts(products);

      if (products.isEmpty()) {
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
      }
      return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
    } catch (Exception e) {
      return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("/product/count/by-brand/and-name")
  public ResponseEntity<ApiResponse> countProductsByBrandAndName(
      @RequestParam String brand, @RequestParam String name) {
    try {
      var productCount = productService.countProductsByBrandAndName(brand, name);
      return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
    } catch (Exception e) {
      return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
    }
  }
}
