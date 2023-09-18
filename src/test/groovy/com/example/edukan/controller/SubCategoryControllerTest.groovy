package com.example.edukan.controller

import com.example.edukan.model.dto.CategoryDto
import com.example.edukan.model.dto.SubCategoryDto
import com.example.edukan.model.exception.NotFoundException
import com.example.edukan.service.SubCategoryService
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class SubCategoryControllerTest extends Specification {
    MockMvc mockMvc
    private SubCategoryService subCategoryService

    void setup() {
        subCategoryService = Mock()
        def subCategoryController = new SubCategoryController(subCategoryService)
        mockMvc = MockMvcBuilders.standaloneSetup(subCategoryController)
                .setControllerAdvice(new ErrorHandler())
                .build()
    }

    def "GetSubCategory 200"() {
        given:
        def id = 1
        def url = "/v1/subcategories/$id"

        def subCategoryDto = new SubCategoryDto("subcategory1")

        def responseJson = '''
                                {
                                "name":"subcategory1"
                                }
                                '''

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        1 * subCategoryService.getSubCategory(id) >> subCategoryDto

        result.status == 200
        JSONAssert.assertEquals(responseJson, result.contentAsString, false)
    }

    def "GetSubCategory 404"() {
        given:
        def id = 1
        def url = "/v1/subcategories/$id"

        when:
        def result = mockMvc
                .perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        1 * subCategoryService.getSubCategory(id) >> {
            throw new NotFoundException("SubCategory doesn't found")
        }

        result.status == HttpStatus.NOT_FOUND.value()
        result.contentAsString == '''{"error":"SubCategory doesn't found"}'''

    }

    def "GetSubCategories 200"() {
        given:
        def url = "/v1/subcategories/"

        def dto = [
                SubCategoryDto.builder()
                        .name("subcategory1")
                        .build(),
                SubCategoryDto.builder()
                        .name("subcategory2")
                        .build()
        ]

        def dtoJson = '''
                             [
                             {
                             "name":"subcategory1"
                             },
                             {
                             "name":"subcategory2"
                             }
                             ]
                             '''

        when:
        def result = mockMvc
                .perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        1 * subCategoryService.getSubCategoryList() >> dto

        result.status == 200
        JSONAssert.assertEquals(dtoJson, result.contentAsString, false)

    }

    def "GetSubCategories empty 200"() {
        given:
        def url = "/v1/subcategories"
        def dtoJSON = '''[]'''

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().response

        then:
        1 * subCategoryService.getSubCategoryList() >> new ArrayList<SubCategoryDto>()

        result.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(dtoJSON, result.contentAsString, false)

    }

    // def "AddSubCategory 200"() {}

    // def "AddSubcategory 404"() {}
}
