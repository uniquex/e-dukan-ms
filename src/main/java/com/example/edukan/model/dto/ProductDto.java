package com.example.edukan.model.dto;

import com.example.edukan.entity.SubCategoryEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String name;
    private String description;
    private Double price;
    private String image_code;

}
