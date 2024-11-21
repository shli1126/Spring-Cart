package com.shoppingcart.cart.service.category;
import com.shoppingcart.cart.exception.AlreadyExistsException;
import com.shoppingcart.cart.exception.ResourceNotFoundException;
import com.shoppingcart.cart.model.Category;
import com.shoppingcart.cart.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category Not Found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c -> !categoryRepository.existByName(c.getName()))
                .map(categoryRepository :: save).orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists"));

    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        return Optional.ofNullable(getCategoryById(categoryId))
                .map(oldCategory -> {oldCategory.setName(category.getName()); return categoryRepository.save(oldCategory);})
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found!"));
    }


    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {throw new ResourceNotFoundException("Category Not Found!");});
    }
}
