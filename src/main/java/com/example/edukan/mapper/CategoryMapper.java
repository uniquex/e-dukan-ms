package com.example.edukan.mapper;

import com.example.edukan.model.dto.CategoryDto;
import com.example.edukan.entity.CategoryEntity;
import com.example.edukan.model.request.CategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class CategoryMapper {
    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    public abstract CategoryEntity categoryRequestToCategoryEntity(CategoryRequest categoryRequest);

    @Mapping(source = "subCategoryEntities", target = "subCategoryDtoList")
    public abstract CategoryDto categoryEntityToCategoryDto(CategoryEntity categoryEntity);

    public abstract List<CategoryDto> categoryEntitiesToCategoryDtoList(List<CategoryEntity> categoryEntities);
}
