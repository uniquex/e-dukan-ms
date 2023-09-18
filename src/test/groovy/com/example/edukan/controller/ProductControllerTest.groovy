package com.example.edukan.controller

import com.example.edukan.entity.ProductEntity
import com.example.edukan.entity.SubCategoryEntity
import com.example.edukan.model.dto.ProductDto
import com.example.edukan.model.exception.NotFoundException
import com.example.edukan.model.request.ProductRequest
import com.example.edukan.service.ProductService
import io.github.benas.randombeans.EnhancedRandomBuilder
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

class ProductControllerTest extends Specification {
    MockMvc mockMvc;
    private ProductService productService
    private random

    void setup() {
        productService = Mock()
        def productController = new ProductController(productService)
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
        .setControllerAdvice(new ErrorHandler())
        .build()
        random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build()
    }

    def "GetProduct 200"() {
        given:
        def id = 1
        def url = "/v1/products/$id"

        def productDto = new ProductDto("alma","meyve",10.45,"34897hjf")

        def responseJson = '''
                                {
                                "name":"alma",
                                "description":"meyve",
                                "price":10.45,
                                "image_code":"34897hjf"
                                }
                                '''
        when:
        def result = mockMvc
                .perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        1 * productService.getProduct(id) >> productDto

        result.status == 200
        JSONAssert.assertEquals(responseJson, result.contentAsString, false)
    }

    def "GetProduct 404"() {
        given:
        def id = 1
        def url = "/v1/products/$id"

        when:
        def result = mockMvc
                .perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        1 * productService.getProduct(id) >> {
            throw new NotFoundException("Product doesn't found")
        }

        result.status == HttpStatus.NOT_FOUND.value()
        result.contentAsString == '''{"error":"Product doesn't found"}'''
    }

    def "GetProducts 200"() {
        given:
        def url = "/v1/products"

        def dto = [
                ProductDto.builder()
                .name("alma")
                .description("meyve")
                .price(10.45)
                .image_code("234oiufn")
                .build(),
                ProductDto.builder()
                .name("armud")
                .description("meyve")
                .price(6.44)
                .image_code("348uojlkf")
                .build()
        ]

        def dtoJSON = '''[{
                            "name":"alma",
                            "description":"meyve",
                            "price":10.45,
                            "image_code":"234oiufn"
                            },
                            {
                            "name":"armud",
                            "description":"meyve",
                            "price":6.44,
                            "image_code":"348uojlkf"
                            }]'''

        when:
        def result = mockMvc
                .perform(MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().response

        then:
        1 * productService.getProducts() >> dto

        result.status == 200
        JSONAssert.assertEquals(dtoJSON, result.contentAsString, false)
    }

    def "GetProducts empty 200"() {
        given:
        def url = "/v1/products"
        def dtoJSON = '''[]'''

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)).andReturn().response

        then:
        1 * productService.getProducts() >> new ArrayList<ProductDto>()

        result.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(dtoJSON, result.contentAsString, false)

    }

    /* def "AddProduct 200"(){
        given:
        def url = "/v1/products/addProduct"

        def newProduct = '''{"name":"alma","description":"meyve","price":10.45}'''

        def file = random.nextObject(MultipartFile)

        when:
        def result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content('{"name":"alma","description":"meyve","price":10.45,' +
                        '"imageCode":"423llf234inflkj"}'))
                .andReturn().response

        then:
        productService.addProduct(newProduct,file)
        result.status == HttpStatus.OK.value()

    }*/

}
