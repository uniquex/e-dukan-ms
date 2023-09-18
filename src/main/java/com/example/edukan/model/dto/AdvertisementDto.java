package com.example.edukan.model.dto;

import com.example.edukan.entity.ProductEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class AdvertisementDto {

    private String type;
    private String image;

    public AdvertisementDto(String type, String image) {
        this.type = type;
        this.image = image;
    }

    private List<ProductEntity> productEntities;

}