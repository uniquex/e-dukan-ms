package com.example.edukan.service

import com.example.edukan.entity.AdvertisementEntity
import com.example.edukan.repository.AdvertisementRepository
import spock.lang.Specification

class AdvertisementServiceTest extends Specification {
    private AdvertisementRepository advertisementRepository;
    private AdvertisementService advertisementService;

    void setup(){
        advertisementRepository = Mock()
        advertisementService = new AdvertisementService(advertisementRepository)
    }

    def "GetAdvertisement success"(){
        given:
        def id = 1
        def entity = AdvertisementEntity.builder()
        .type("good")
        .image("elrfnljd14lnl")
        .build()

        when:
        def dto = advertisementService.getAdvertisement(id)

        then:
        1 * advertisementRepository.findById(id) >> Optional.of(entity)

        dto.type == entity.type
        dto.image == entity.image
    }

    def "getAllAdvertisements success"(){
        given:
        def entity =[
                AdvertisementEntity.builder()
                .type("good")
                .image("3450388mfndsaf")
                        .build(),
                AdvertisementEntity.builder()
                .type("excellent")
                .image("49nvjk0943")
                .build()
        ]
        when:
        def dto = advertisementService.getAllAdvertisements()

        then:
        1 * advertisementRepository.findAll() >> entity

        dto[0].type == entity[0].type
        dto[0].image == entity[0].image
        dto[1].type == entity[1].type
        dto[1].image == entity[1].image

    }
}
