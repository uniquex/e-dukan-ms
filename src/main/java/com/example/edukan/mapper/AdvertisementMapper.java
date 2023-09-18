package com.example.edukan.mapper;

import com.example.edukan.entity.AdvertisementEntity;
import com.example.edukan.model.dto.AdvertisementDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class AdvertisementMapper {

    public static final AdvertisementMapper INSTANCE = Mappers.getMapper(AdvertisementMapper.class);

    public abstract AdvertisementDto entityToDto (AdvertisementEntity entity);
    public abstract List<AdvertisementDto> entityToDtoList (List<AdvertisementEntity> entities);

}