package com.adesh.expense_tracker.controller;

import com.adesh.expense_tracker.entity.Category;
import com.adesh.expense_tracker.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // POST /categories
    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    // GET /categories
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
