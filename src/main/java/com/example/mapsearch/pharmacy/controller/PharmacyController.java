package com.example.mapsearch.pharmacy.controller;

import com.example.mapsearch.direction.response.DirectionRes;
import com.example.mapsearch.pharmacy.service.PharmacyRecommendationService;
import com.example.mapsearch.pharmacy.service.PharmacyRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/pharmacy")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRecommendationService recommendPharmacyService;




    @GetMapping("/recommend/{address}")
    public List<DirectionRes> getRecommendPharmacyList(@RequestParam String address) {
        return recommendPharmacyService.recommendPharmacy(address);
    }

}
