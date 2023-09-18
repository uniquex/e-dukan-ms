package com.example.edukan.controller;

import com.example.edukan.model.dto.CategoryDto;
import com.example.edukan.model.request.CategoryRequest;
import com.example.edukan.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {
    public final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("{id}")
    public CategoryDto getCategory(@PathVariable("id") Long id) {
        return categoryService.getCategory(id);
    }

    @GetMapping()
    public List<CategoryDto> getCategories() {
        return categoryService.getCategoryList();
    }
    @PostMapping
    public void addCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.addCategory(categoryRequest);
    }
}
