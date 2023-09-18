package com.example.edukan.controller

import com.example.edukan.model.dto.AdvertisementDto
import com.example.edukan.model.dto.CategoryDto
import com.example.edukan.model.exception.NotFoundException
import com.example.edukan.model.request.CategoryRequest
import com.example.edukan.service.CategoryService
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class CategoryControllerTest extends Specification {
    MockMvc mockMvc
    private CategoryService categoryService

    void setup() {
        categoryService = Mock()
        def categoryController = new CategoryController(categoryService)
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new ErrorHandler())
                .build()
    }

    def "GetCategory 200"() {
        given:
        def id = 1
        def url = "/v1/categories/$id"

        def categoryDto = new CategoryDto("category1")

        def responseJson = '''
                                {
                                "name":"category1"
                                }
                              '''

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        1 * categoryService.getCategory(id) >> categoryDto

        result.status == 200
        JSONAssert.assertEquals(responseJson, result.contentAsString, false)
    }

    def "GetCategory 404"() {
        given:
        def id = 1
        def url = "/v1/categories/$id"

        when:
        def result = mockMvc
                .perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        1 * categoryService.getCategory(id) >> {
            throw new NotFoundException("Category doesn't found")
        }

        result.status == HttpStatus.NOT_FOUND.value()
        result.contentAsString == '''{"error":"Category doesn't found"}'''
    }

    def "GetCategories 200"() {
        given:
        def url = "/v1/categories/"

        def dto = [
                CategoryDto.builder()
                        .name("category1")
                        .build(),
                CategoryDto.builder()
                        .name("category2")
                        .build()
        ]

        def dtoJSON = '''[
                            {
                             "name":"category1"
                             }
                             ,{
                             "name":"category2"
                            }
                            ]'''

        when:
        def result = mockMvc
                .perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        1 * categoryService.getCategoryList() >> dto

        result.status == 200
        JSONAssert.assertEquals(dtoJSON, result.contentAsString, false)
    }

    def "GetCategories empty 200"() {
        given:
        def url = "/v1/categories"
        def dtoJSON = '''[]'''

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().response

        then:
        1 * categoryService.getCategoryList() >> new ArrayList<CategoryDto>()

        result.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(dtoJSON, result.contentAsString, false)
    }

    def "AddCategory 200"() {
        given:
        def url = "/v1/categories"
        def categoryRequest = CategoryRequest.builder()
                .name("category1")
                .build()

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"name":"test"}'))
                .andReturn().response

        then:
        categoryService.addCategory(categoryRequest)
        result.status == HttpStatus.OK.value()
    }

    //def "AddCategory 404"() {} nece error vere biler bu metod?
}
