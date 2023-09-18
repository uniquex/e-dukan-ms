package com.example.edukan.controller;

import com.example.edukan.model.dto.SubCategoryDto;
import com.example.edukan.model.request.SubCategoryRequest;
import com.example.edukan.service.SubCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/subcategories")
public class SubCategoryController {
    public final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @GetMapping("{id}")
    public SubCategoryDto getSubCategory(@PathVariable("id") Long id) {
        return subCategoryService.getSubCategory(id);
    }

    @GetMapping()
    public List<SubCategoryDto> getSubCategories() {
        return subCategoryService.getSubCategoryList();
    }

    @PostMapping
    public void addSubCategory(@RequestBody SubCategoryRequest subCategoryRequest) {
        subCategoryService.addSubCategory(subCategoryRequest);
    }
}
