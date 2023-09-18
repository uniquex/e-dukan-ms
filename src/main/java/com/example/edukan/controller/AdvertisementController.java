package com.example.edukan.controller;


import com.example.edukan.model.dto.AdvertisementDto;
import com.example.edukan.service.AdvertisementService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/advertisements")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping("{id}")
    public AdvertisementDto getAdvertisement(@PathVariable Long id) {
        return advertisementService.getAdvertisement(id);
    }

    @GetMapping
    public List<AdvertisementDto> getAllAdvertisements() {
        return advertisementService.getAllAdvertisements();
    }

    @PostMapping("/add")
    public String addAdvertisement(@RequestPart("request") String advertisement,
                                   @RequestPart("image") MultipartFile file) throws IOException {

        return advertisementService.addAdvertisement(advertisement, file);
    }
}

