package com.example.edukan.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    String name ;
    List<SubCategoryDto> subCategoryDtoList;

    public CategoryDto(String name) {
        this.name = name;
    }
}
