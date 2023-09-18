package com.example.edukan.service

import com.example.edukan.entity.CategoryEntity
import com.example.edukan.entity.SubCategoryEntity
import com.example.edukan.mapper.CategoryMapper
import com.example.edukan.mapper.SubCategoryMapper
import com.example.edukan.model.dto.CategoryDto
import com.example.edukan.model.request.SubCategoryRequest
import com.example.edukan.repository.SubCategoryRepository
import spock.lang.Specification

class SubCategoryServiceTest extends Specification {
    private SubCategoryRepository subCategoryRepository
    private SubCategoryService subCategoryService

    void setup(){
        subCategoryRepository = Mock()
        subCategoryService = new SubCategoryService(subCategoryRepository)
    }

    def "GetSubCategory success"(){
        given:
        def id = 1
        def entity = SubCategoryEntity.builder()
        .name("subcategory1")
        .build()

        when:
        def dto = subCategoryService.getSubCategory(id)

        then:
        1 * subCategoryRepository.findById(id) >> Optional.of(entity)

        dto.name == entity.name
    }

    def "GetSubCategoryList success"(){
        given:
        def entity = [
                SubCategoryEntity.builder()
                        .name("subcategory1")
                        .build(),
                SubCategoryEntity.builder()
                        .name("subcategory2")
                        .build()
        ]

        when:
        def dto = subCategoryService.getSubCategoryList()

        then:
        1 * subCategoryRepository.findAll() >> entity

        dto[0].name == entity[0].name
        dto[1].name == entity[1].name
    }

    def "AddSubCategory success"(){
        given:
        def dto = SubCategoryRequest.builder()
                .name("subcategory1")
                .categoryId(1)
                .build()

        when:
        def result = subCategoryService.addSubCategory(dto)

        then:
        1 * subCategoryRepository.save(SubCategoryMapper.INSTANCE.subCategoryRequestToSubCategoryEntity(dto))

    }
}
