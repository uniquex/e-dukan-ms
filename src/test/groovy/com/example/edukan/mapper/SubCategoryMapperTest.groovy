package com.example.edukan.mapper

import com.example.edukan.entity.SubCategoryEntity
import com.example.edukan.model.request.SubCategoryRequest
import spock.lang.Specification

class SubCategoryMapperTest extends Specification {
    def "SubCategoryRequestToSubCategoryEntity success"(){
        given:
        def request = SubCategoryRequest.builder()
        .name("subcategory1")
        .categoryId(1)
        .build()

        when:
        def result = SubCategoryMapper.INSTANCE.subCategoryRequestToSubCategoryEntity(request)

        then:
        result.name == request.name
        result.categoryEntity.getId() == request.categoryId

    }

    def "SubCategoryEntityToSubCategoryDto success"(){
        given:
        def entity = SubCategoryEntity.builder()
        .name("subcategory1")
        .build()

        when:
        def result = SubCategoryMapper.INSTANCE.subCategoryEntityToSubCategoryDto(entity)

        then:
        entity.name == result.name

    }

    def "SubCategoryEntitiesToSubCategoryDtoList success"(){
        given:
        def entities = [
                SubCategoryEntity.builder()
                .name("subcategory1")
                .build(),
                SubCategoryEntity.builder()
                .name("subcategory2")
                .build()
        ]

        when:
        def result = SubCategoryMapper.INSTANCE.subCategoryEntitiesToSubCategoryDtoList(entities)

        then:
        result[0].name == entities[0].name
        result[1].name == entities[1].name

    }
}
