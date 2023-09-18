package com.example.edukan.service;

import com.example.edukan.entity.ProductEntity;
import com.example.edukan.mapper.ProductMapper;
import com.example.edukan.model.dto.ProductDto;
import com.example.edukan.model.exception.NotFoundException;
import com.example.edukan.model.request.ProductRequest;
import com.example.edukan.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto getProduct(Long id) {
        log.info("Action.log.getProduct method started");
        return ProductMapper.INSTANCE.entityToDto(productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product doesn't found")));
    }

    public List<ProductDto> getProducts() {
        log.info("Action.log.getProduct method started");
        return ProductMapper.INSTANCE.entityToDtoList(productRepository.findAll());
    }

    public List<ProductDto> getProductsInPriceRange(double minPrice,
                                                    double maxPrice) {

        return ProductMapper.INSTANCE.entityToDtoList(productRepository.findByPriceBetween(minPrice, maxPrice));
    }
    public ProductRequest addProduct (String product,
                                  MultipartFile file) throws IOException {
        log.info("Action.log.addProduct method started");
        ProductRequest productJson = new ProductRequest();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            productJson = objectMapper.readValue(product, ProductRequest.class);
        }catch (IOException e){
            log.error("Action.log.addProduct something went wrong!");
            System.out.println(e.getMessage());
        }
        byte[] imageArr = file.getBytes();
        String imageAsString= Base64.encodeBase64String(imageArr);
        productJson.setImageCode(imageAsString);

        productRepository.save(ProductMapper.INSTANCE.requestToEntity(productJson));
        log.info("Action.log.addProduct product was saved successfully!");
        return productJson;
    }

    public void deleteProduct(Long id) {
        log.info("Action.log.deleteProduct method started");
        ProductEntity deletedEntity = productRepository.findById(id).orElseThrow(() ->
                new NotFoundException("THIS ID DOES NOT EXIST!")
        );
        productRepository.delete(deletedEntity);
        System.out.println("Product was deleted successfully!");
        log.info("Action.log.deleteProduct method finished");
    }

    public ResponseEntity<ProductEntity> updateProduct(Long id, ProductRequest request) {
        log.info("Action.log.updateProduct method started");

        ProductEntity updatedProduct = productRepository.findById(id).orElseThrow(() ->
                new NotFoundException("THIS ID DOES NOT EXIST!")
        );
        updatedProduct.setName(request.getName());
        updatedProduct.setDescription(request.getDescription());
        updatedProduct.setPrice(request.getPrice());
        productRepository.save(updatedProduct);
        log.info("Action.log.updateProduct method finished");
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    public ProductEntity updateOneColumn(Long id, Double price) {
        log.info("Action.log.updateOneColumn method started");
        ProductEntity product = productRepository.findById(id).get();
        product.setPrice(price);
        productRepository.save(product);
        log.info("Action.log.updateProduct method finished, column was updated successfully");
        return product;
    }
}