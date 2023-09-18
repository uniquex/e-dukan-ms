package com.example.edukan.service;

import com.example.edukan.model.dto.SubCategoryDto;
import com.example.edukan.mapper.SubCategoryMapper;
import com.example.edukan.model.exception.NotFoundException;
import com.example.edukan.model.request.SubCategoryRequest;
import com.example.edukan.repository.SubCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubCategoryService {

    public final SubCategoryRepository subCategoryRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    public SubCategoryDto getSubCategory (Long id){
        log.info("Action.log.getSubCategory method started");
        return SubCategoryMapper.INSTANCE.subCategoryEntityToSubCategoryDto(
                subCategoryRepository.findById(id)
                        .orElseThrow(()-> new NotFoundException("SubCategory doesn't found"))
        );
    }
    public List<SubCategoryDto> getSubCategoryList(){
        log.info("Action.log.getSubCategoryList method started");
        return SubCategoryMapper.INSTANCE.subCategoryEntitiesToSubCategoryDtoList(
                subCategoryRepository.findAll());
    }
    public void addSubCategory(SubCategoryRequest subCategoryRequest){
        log.info("Action.log.addSubCategory method started");
        subCategoryRepository.save(
                SubCategoryMapper.INSTANCE.subCategoryRequestToSubCategoryEntity(subCategoryRequest)
        );
        log.info("Action.log.addSubCategory method finished");
    }
}
