package com.example.edukan.mapper

import com.example.edukan.entity.CategoryEntity
import com.example.edukan.model.request.CategoryRequest
import spock.lang.Specification

class CategoryMapperTest extends Specification {

    def "CategoryRequestToCategoryEntity success"(){
        given:
        def request = CategoryRequest.builder()
        .name("category1")
        .build()

        when:
        def result = CategoryMapper.INSTANCE.categoryRequestToCategoryEntity(request)

        then:
        result.name == request.name
    }

    def "CategoryEntityToCategoryDto success"(){
        given:
        def entity = CategoryEntity.builder()
        .name("category1")
        .build()

        when:
        def result = CategoryMapper.INSTANCE.categoryEntityToCategoryDto(entity)

        then:
        result.name == entity.name
    }

    def "CategoryEntitiesToCategoryDtoList success"(){
        given:
        def entities = [
                CategoryEntity.builder()
                .name("category1")
                .build(),
                CategoryEntity.builder()
                .name("category2")
                .build()
                ]

        when:
        def result = CategoryMapper.INSTANCE.categoryEntitiesToCategoryDtoList(entities)

        then:
        entities[0].name == result[0].name
        entities[1].name == result[1].name
    }
}
