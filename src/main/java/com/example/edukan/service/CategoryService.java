package com.example.edukan.service;

import com.example.edukan.model.dto.CategoryDto;
import com.example.edukan.mapper.CategoryMapper;
import com.example.edukan.model.exception.NotFoundException;
import com.example.edukan.model.request.CategoryRequest;
import com.example.edukan.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class CategoryService {
    public final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDto getCategory(Long id){
        log.info("Action.log.getCategory method started");
        return CategoryMapper.INSTANCE.categoryEntityToCategoryDto(
                categoryRepository.findById(id).
                        orElseThrow(()-> new NotFoundException("Category doesn't found")));
    }
    public List<CategoryDto> getCategoryList(){
        log.info("Action.log.getCategoryList method started");
        return CategoryMapper.INSTANCE.categoryEntitiesToCategoryDtoList(
                categoryRepository.findAll()
        );
    }
    public void addCategory(CategoryRequest categoryRequest){
        log.info("Action.log.addCategory method started");
        categoryRepository.save(
                CategoryMapper.INSTANCE.categoryRequestToCategoryEntity(categoryRequest)
        );
        log.info("Action.log.addCategory method finished");
    }
}
