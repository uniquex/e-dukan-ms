package com.example.edukan.mapper;

import com.example.edukan.model.dto.SubCategoryDto;
import com.example.edukan.entity.SubCategoryEntity;
import com.example.edukan.model.request.SubCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class SubCategoryMapper {
    public static final SubCategoryMapper INSTANCE = Mappers.getMapper(SubCategoryMapper.class);

    @Mapping(source = "categoryId",target ="categoryEntity.id" )
    public abstract SubCategoryEntity subCategoryRequestToSubCategoryEntity(SubCategoryRequest subCategoryRequest);

    @Mapping(source = "products", target = "products")
    public abstract SubCategoryDto subCategoryEntityToSubCategoryDto(SubCategoryEntity subCategory);

    public abstract List<SubCategoryDto> subCategoryEntitiesToSubCategoryDtoList(List<SubCategoryEntity> subCategoryEntities);
}


