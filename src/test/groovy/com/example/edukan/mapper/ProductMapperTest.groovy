package com.example.edukan.mapper

import com.example.edukan.entity.ProductEntity
import spock.lang.Specification

class ProductMapperTest extends Specification {
    def "EntityToDto succes"() {
        given:
        def entity = ProductEntity.builder()
                .name("alma")
                .description("meyve")
                .price(10.45)
                .build()

        when:
        def result = ProductMapper.INSTANCE.entityToDto(entity)

        then:
        result.name == entity.name
        result.description == entity.description
        result.price == entity.price

    }

    def "EntityToDtoList succes"() {
        given:
        def entities = [
                ProductEntity.builder()
                        .name("alma")
                        .description("meyve")
                        .price(10.45)
                        .build(),
                ProductEntity.builder()
                        .name("armud")
                        .description("meyve")
                        .price(5.65)
                        .build()
        ]

        when:
        def result = ProductMapper.INSTANCE.entityToDtoList(entities)

        then:
        result[0].name == entities[0].name
        result[0].description == entities[0].description
        result[0].price == entities[0].price
        result[1].name == entities[1].name
        result[1].description == entities[1].description
        result[1].price == entities[1].price

    }
}