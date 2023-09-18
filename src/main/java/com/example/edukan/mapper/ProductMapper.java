package com.example.edukan.mapper;

import com.example.edukan.entity.ProductEntity;
import com.example.edukan.model.dto.ProductDto;
import com.example.edukan.model.request.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class ProductMapper {

    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Mapping(source = "subCategoryId",target ="subEntity.id" )
    public abstract ProductEntity requestToEntity(ProductRequest productRequest);

    public abstract ProductDto entityToDto (ProductEntity entity);

    public abstract List<ProductDto> entityToDtoList(List<ProductEntity> entities);

}