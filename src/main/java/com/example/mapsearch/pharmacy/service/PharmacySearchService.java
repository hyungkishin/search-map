package com.example.mapsearch.pharmacy.service;


import com.example.mapsearch.pharmacy.dto.PharmacyDto;
import com.example.mapsearch.pharmacy.entity.Pharmacy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacySearchService {

    private final PharmacyRepositoryService pharmacyRepositoryService;

    public List<PharmacyDto> searchPharmacyList() {
        final List<Pharmacy> pharmacyList = pharmacyRepositoryService.findAll();

        return pharmacyList.stream()
                .map(entity -> PharmacyDto.of(entity))
                .collect(Collectors.toList());
    }

}
