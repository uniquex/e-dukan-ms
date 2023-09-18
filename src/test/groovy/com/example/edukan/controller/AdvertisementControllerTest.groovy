package com.example.edukan.controller

import com.example.edukan.entity.ProductEntity
import com.example.edukan.model.dto.AdvertisementDto
import com.example.edukan.model.exception.NotFoundException
import com.example.edukan.service.AdvertisementService
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class AdvertisementControllerTest extends Specification {
    MockMvc mockMvc
    private AdvertisementService advertisementService

    void setup(){
        advertisementService = Mock()
        def advertisementController = new AdvertisementController(advertisementService)
        mockMvc = MockMvcBuilders.standaloneSetup(advertisementController)
                    .setControllerAdvice(new ErrorHandler())
                    .build()
    }

    def "GetAdvertisement 200"(){
        given:
        def id = 1
        def url = "/v1/advertisements/$id"
        def advertisementDto = new AdvertisementDto("good","8939hf")

        def responseJson = '''
                                  {
                                  "type":"good",
                                  "image":"8939hf"
                                    }
                              '''
        when:
        def result = mockMvc
        .perform(MockMvcRequestBuilders.get(url)
        .contentType(MediaType.APPLICATION_JSON))
        .andReturn().response

        then:
        1 * advertisementService.getAdvertisement(id) >> advertisementDto

        result.status == 200
        JSONAssert.assertEquals(responseJson, result.contentAsString, false)
    }

    def "GetAdvertisement 400"(){
        given:
        def id = 1
        def url = "/v1/advertisements/$id"

        when:
        def result = mockMvc
                                .perform(MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_JSON))
        .andReturn().response

        then:
        1 * advertisementService.getAdvertisement(id) >> {
            throw new NotFoundException("Advertisement doesn't found")
        }

        result.status == HttpStatus.NOT_FOUND.value()
        result.contentAsString == '''{"error":"Advertisement doesn't found"}'''
    }

    def "GetAllAdvertisements 200"(){
        given:
        def url = "/v1/advertisements"
        def dto = [AdvertisementDto.builder()
                .type("excellent")
                .image("lfkjekr34")
                .build(),
                AdvertisementDto.builder()
                .type("good")
                .image("43kljejlr")
                .build()
        ]
        def dtoJSON = '''
                            [
                            {
                            "type":"excellent",
                            "image":"lfkjekr34"
                            },
                            {
                            "type":"good",
                            "image":"43kljejlr"
                            }
                            ]
                        '''
        when:
        def result = mockMvc
        .perform(MockMvcRequestBuilders.get(url)
        .contentType(MediaType.APPLICATION_JSON))
        .andReturn().response

        then:
        1 * advertisementService.getAllAdvertisements() >> dto

        result.status == 200
        JSONAssert.assertEquals(dtoJSON, result.contentAsString, false)

    }

    def "GetAllAdvertisements empty 200"(){
        given:
        def url = "/v1/advertisements"
        def dtoJSON = '''[]'''

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(url)
        .contentType(MediaType.APPLICATION_JSON)).andReturn().response

        then:
        1 * advertisementService.getAllAdvertisements() >> new ArrayList<AdvertisementDto>()

        result.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(dtoJSON, result.contentAsString, false)
    }


}
