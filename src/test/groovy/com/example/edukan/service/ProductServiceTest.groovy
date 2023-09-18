package com.example.edukan.service

import com.example.edukan.entity.ProductEntity
import com.example.edukan.mapper.ProductMapper
import com.example.edukan.model.dto.ProductDto
import com.example.edukan.repository.ProductRepository
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

class ProductServiceTest extends Specification {
    private ProductRepository productRepository
    private ProductService productService
    private random

    void setup(){
        productRepository = Mock()
        productService = new ProductService(productRepository)
        random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build()
    }

    def "GetProduct success"(){
        given:
        def id = 1
        def entity = ProductEntity.builder()
        .name("alma")
        .description("meyve")
        .price(10.45)
                .build()

        when:
        def dto = productService.getProduct(id)

        then:
        1 * productRepository.findById(id) >> Optional.of(entity)

        dto.name == entity.name
        dto.description == entity.description
        dto.price == entity.price
    }

    def "GetProducts success"(){
        given:
        def entity =[
                ProductEntity.builder()
                .name("alma")
                        .description("meyve")
                        .price(10.45)
                        .build(),
                ProductEntity.builder()
                .name("armud")
                        .description("meyve")
                        .price(5.75)
                        .build()
        ]

        when:
        def dto = productService.getProducts()

        then:
        1 * productRepository.findAll() >> entity

        dto[0].name == entity[0].name
        dto[0].description == entity[0].description
        dto[0].price == entity[0].price
        dto[1].name == entity[1].name
        dto[1].description == entity[1].description
        dto[1].price == entity[1].price
    }

/*    def "GetJson success"(){
       given:
       def dto = random.nextObject(ProductDto)

       def file = random.nextObject(ProductEntity)

       when:
       productService.getJson(dto,file)

       then:
       1 * productRepository.save(dto)

   }*/

/*    def "DeleteProduct"(){
        given:
        def id = 1

        when:
        def dto = productService.deleteProduct(id)

        then:
        1 * productRepository.findById(id)
        1 * productRepository.deleteById(id) >> Optional.of(dto)

    }*/
}
