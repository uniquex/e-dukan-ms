package com.example.edukan.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryDto {
    Long id;
    String name;
    List<ProductDto> products;

    public SubCategoryDto(String name) {
        this.name = name;
    }
}
