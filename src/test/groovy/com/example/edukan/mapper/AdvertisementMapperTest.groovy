package com.example.edukan.mapper

import com.example.edukan.entity.AdvertisementEntity
import spock.lang.Specification

class AdvertisementMapperTest extends Specification {

    def "entityToDto success"(){
        given:
        def entity = AdvertisementEntity.builder()
        .type("good")
        .image("asklflask34")
        .build()

        when:
        def result = AdvertisementMapper.INSTANCE.entityToDto(entity)

        then:
        entity.type == result.type
        entity.image == result.image
    }

    def "entityToDtoList success"(){
        given:
        def entities = [
                AdvertisementEntity.builder()
                .type("good")
                .image("34oiernjfla")
                .build(),
                AdvertisementEntity.builder()
                .type("excellent")
                .image("409rnfkajsdfn")
                .build()
        ]

        when:
        def result = AdvertisementMapper.INSTANCE.entityToDtoList(entities)

        then:
        entities[0].type == result[0].type
        entities[0].image == result[0].image
        entities[1].type == result[1].type
        entities[1].image == result[1].image
    }
}
