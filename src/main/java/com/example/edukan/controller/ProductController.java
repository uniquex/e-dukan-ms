package com.example.edukan.controller;


import com.example.edukan.entity.ProductEntity;
import com.example.edukan.model.dto.ProductDto;
import com.example.edukan.model.request.ProductRequest;
import com.example.edukan.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @GetMapping
    public List<ProductDto> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/filter")
    public List<ProductDto> getProductsInPriceRange(@RequestParam("minPrice") double minPrice,
                                                    @RequestParam("maxPrice") double maxPrice){

        return productService.getProductsInPriceRange(minPrice,maxPrice);
    }

    @PostMapping(value = "/addProduct",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE})
    public ProductRequest addProduct(@RequestPart("request") String product,
                                    @RequestPart("image") MultipartFile file) throws IOException {

        return productService.addProduct(product, file);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id,
                                                       @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @PatchMapping("/updateProduct/{id}/{price}")
    public ProductEntity updateOneColumn(@PathVariable Long id,
                                         @PathVariable Double price) {
        return productService.updateOneColumn(id, price);
    }
}