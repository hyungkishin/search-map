package com.example.mapsearch.pharmacy.service;

import com.example.mapsearch.pharmacy.entity.Pharmacy;
import com.example.mapsearch.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PharmacyRepositoryService {

    private final PharmacyRepository pharmacyRepository;

    public List<Pharmacy> findAll() {
        return pharmacyRepository.findAll();
    }
}
