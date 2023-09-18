package com.example.edukan.service;

import com.example.edukan.entity.AdvertisementEntity;
import com.example.edukan.entity.ProductEntity;
import com.example.edukan.mapper.AdvertisementMapper;
import com.example.edukan.model.dto.AdvertisementDto;
import com.example.edukan.model.exception.NotFoundException;
import com.example.edukan.repository.AdvertisementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public AdvertisementDto getAdvertisement (Long id){
        log.info("Action.log.getAdvertisement method started");
        return AdvertisementMapper.INSTANCE.entityToDto(advertisementRepository.findById(id).
                orElseThrow(()-> new NotFoundException("Advertisement doesn't found")));
    }

    public List<AdvertisementDto> getAllAdvertisements (){
        log.info("Action.log.getAllAdvertisements method started");
        return AdvertisementMapper.INSTANCE.entityToDtoList(advertisementRepository.findAll());
    }

    public String addAdvertisement (String product,
                                     MultipartFile file) throws IOException {
        log.info("Action.log.addAdvertisement method started");
        AdvertisementEntity advertisementJson = new AdvertisementEntity();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            advertisementJson = objectMapper.readValue(product, AdvertisementEntity.class);
        }catch (IOException e){
            log.error("Action.log.addAdvertisement something went wrong!");
            System.out.println(e.getMessage());
        }
        byte[] imageArr = file.getBytes();
        String imageAsString= Base64.encodeBase64String(imageArr);
        advertisementJson.setImage(imageAsString);
        advertisementRepository.save(advertisementJson);
        log.info("Action.log.addAdvertisement advertisement was saved succesfully!");
        return "Advertisement was added succesfully!";
    }

}