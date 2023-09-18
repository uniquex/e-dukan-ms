package com.example.edukan.mapper

import com.example.edukan.entity.UserEntity
import com.example.edukan.model.request.UserRequest
import spock.lang.Specification

class UserMapperTest extends Specification {
    def "EntityToDto success"(){
        given:
        def entity = UserEntity.builder()
        .name("hasan")
        .surname("aliyev")
        .email("hasanaliev1042@gmail.com")
        .phone("0503738070")
        .address("khirdalan")
        .build()

        when:
        def result = UserMapper.INSTANCE.entityToDto(entity)

        then:
        result.name == entity.name
        result.surname == entity.surname
        result.email == entity.email
        result.phone == entity.phone
        result.address == entity.address
    }

    def "EntityToDtoList success"(){
        given:
        def entities = [
                UserEntity.builder()
                        .name("hasan")
                        .surname("aliyev")
                        .email("hasanaliev1042@gmail.com")
                        .phone("0503738070")
                        .address("khirdalan")
                .build(),
                UserEntity.builder()
                        .name("musa").surname("amiraslanov")
                        .email("musaamiraslanov@gmail.com")
                        .phone("0103145545")
                        .address("nzs")
                        .build()
                ]

        when:
        def result = UserMapper.INSTANCE.entityToDtoList(entities)

        then:
        entities[0].name == result[0].name
        entities[0].surname == result[0].surname
        entities[0].email == result[0].email
        entities[0].phone == result[0].phone
        entities[0].address == result[0].address
        entities[1].name == result[1].name
        entities[1].surname == result[1].surname
        entities[1].email == result[1].email
        entities[1].phone == result[1].phone
        entities[1].address == result[1].address

    }

    def "RequestToEntity success"(){
        given:
        def request = UserRequest.builder()
                .name("hasan")
                .surname("aliyev")
                .email("hasanaliev1042@gmail.com")
                .phone("0503738070")
                .address("khirdalan")
                .build()

        when:
        def result = UserMapper.INSTANCE.requestToEntity(request)

        then:
        request.name == result.name
        request.surname == result.surname
        request.email == result.email
        request.phone == result.phone
        request.address == result.address

    }

}
