package com.example.edukan.mapper;

import com.example.edukan.model.dto.UserDto;
import com.example.edukan.model.request.UserRequest;
import com.example.edukan.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    public abstract UserDto entityToDto (UserEntity entity);
    public abstract List<UserDto> entityToDtoList (List<UserEntity> entities);
    public abstract UserEntity requestToEntity (UserRequest request);

}