package com.example.edukan.service

import com.example.edukan.entity.CategoryEntity
import com.example.edukan.mapper.CategoryMapper
import com.example.edukan.model.dto.CategoryDto
import com.example.edukan.model.request.CategoryRequest
import com.example.edukan.repository.CategoryRepository
import spock.lang.Specification

class CategoryServiceTest extends Specification {

    private CategoryRepository categoryRepository
    private CategoryService categoryService

    void setup() {
        categoryRepository = Mock()
        categoryService = new CategoryService(categoryRepository)
    }

    def "GetCategory success"(){
        given:
        def id = 1
        def entity = CategoryEntity.builder()
                .name("category1")
                .build()
        when:
        def dto = categoryService.getCategory(id)

        then:
        1 * categoryRepository.findById(id) >> Optional.of(entity)

        dto.name == entity.name
    }

    def "GetCategoryList success"(){
        given:
        def entity = [
                CategoryEntity.builder()
                .name("category1")
                .build(),
                CategoryEntity.builder()
                .name("category2")
                .build()
        ]

        when:
        def dto = categoryService.getCategoryList()

        then:
        1 * categoryRepository.findAll() >> entity

        dto[0].name == entity[0].name
        dto[1].name == entity[1].name
    }

    def "AddCategory success"(){
        given:
        def dto = CategoryRequest.builder()
        .name("category1")
        .build()

        when:
        def result = categoryService.addCategory(dto)

        then:
        1 * categoryRepository.save(CategoryMapper.INSTANCE.categoryRequestToCategoryEntity(dto))


    }

}
